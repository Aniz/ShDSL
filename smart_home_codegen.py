"""
Generate java code from textX model using jinja2
template engine (http://jinja.pocoo.org/docs/dev/)
"""
from os import mkdir, walk
from os.path import exists, dirname, join
import jinja2
import pydot
from smart_home_test import get_smart_home_mm
from pprint import pprint
import shutil
import sys
import re
from textx.metamodel import metamodel_from_file
from textx.export import metamodel_export, model_export

def main(debug=False):

    this_folder = dirname(__file__)
    smart_home_mm = get_smart_home_mm(debug)

    # Build Event model from person.ent file
    smart_home_model = smart_home_mm.model_from_file(join(this_folder, 'smart_home.shome'))

    def javatype(s):
        """
        Maps type names from PrimitiveType to Java.
        """
        return {
                'integer': 'int',
                'string': 'String'
        }.get(s.name, s.name)

    # Create output folders
    srcgen_folder = join(this_folder, 'srcgen')
    if exists(srcgen_folder):
        shutil.rmtree(srcgen_folder)

    systemName = smart_home_model.name
    systemPin = smart_home_model.pin
    general_folder = createFolder(this_folder, 'srcgen')
    src_folder = createFolder(general_folder, 'src')
    general_sh_folder = createFolder(src_folder, systemName.lower())
    srcgen_folder = createFolder(general_sh_folder, 'smarthome')
    
    dotFolder = createFolder(srcgen_folder, 'dot')
    createDotFiles(smart_home_mm,smart_home_model,dotFolder)
    
    arduinoFolder = createFolder(srcgen_folder, 'arduino')
    enumsFolder = createFolder(srcgen_folder, 'enums')
    featureModelingFolder = createFolder(srcgen_folder, 'featureModeling')
    featuresFolder = createFolder(srcgen_folder, 'features')
    guiFolder = createFolder(srcgen_folder, 'gui')
    uiFolder = createFolder(srcgen_folder, 'featuresUI')
    utilFolder = createFolder(srcgen_folder,'util')
    modelFolder = createFolder(srcgen_folder, 'model')
    devicesFolder = createFolder(modelFolder,'devices')
    homeFolder = createFolder(modelFolder,'home')
    
    codeFolder = join(this_folder, 'SmartHome')
    srcCodeFolder = createFolder(codeFolder, 'src')
    riseCodeFolder = createFolder(srcCodeFolder, 'rise')
    smarthomeCodeFolder = createFolder(riseCodeFolder, 'smarthome')
    arduinoCodeFolder = createFolder(smarthomeCodeFolder, 'arduino')
    enumsCodeFolder = createFolder(smarthomeCodeFolder, 'enums')
    featureModelingCodeFolder = createFolder(smarthomeCodeFolder, 'featureModeling')
    featuresCodeFolder = createFolder(smarthomeCodeFolder, 'features')
    guiCodeFolder = createFolder(smarthomeCodeFolder, 'gui')
    uiCodeFolder = createFolder(smarthomeCodeFolder, 'featuresUI')
    utilCodeFolder = createFolder(smarthomeCodeFolder, 'util')
    modelCodeFolder = createFolder(smarthomeCodeFolder, 'model')
    devicesCodeFolder = createFolder(modelCodeFolder,'devices')
    homeCodeFolder = createFolder(modelCodeFolder,'home')
    
    # Get template folders
    templateFolder = join(this_folder, 'templates')
    
    # Initialize template engine.
    jinja_env = jinja2.Environment(
        loader=jinja2.FileSystemLoader(this_folder),
        trim_blocks=True,
        lstrip_blocks=True)
    
    # Register filter for mapping event type names to Java type names.
    jinja_env.filters['javatype'] = javatype
    jinja_env.filters['upperfirst'] = upperfirst
    jinja_env.filters['splitName'] = splitName
    jinja_env.filters['remove'] = remove
    jinja_env.filters['append'] = append

    componentData = ""
    componentExtraData = ""
    
    selectedOptionsArray = []
    actionsArray = []
    devicesArray = []
    commandsArray = []
    commandsOptionArray = []

    componentDict = {}
    statmentDict = {}
    commandsDict = {}
    componentDictByBaseDevice = {}
    componentFeatureDict = {}
    commandsOptionDict = {}
    dependenceDict = {}
    allDevicesDict = {}

    avaliableBaseDevices = ["Actuator","Sensor","Hardware"]
    avaliableOptions = ["User","Speaker","Organizer","Event","Payment","Reviewer","Activity","Submission","Review","Author","Receipt","CheckingCopy"]
    avaliableFunctions = ["Insert","Remove","Update","Search","ListAll","Search","Management"]
    avaliableDependencesArray = ["Review","ActivityUser","ActivitySpeaker","ActivityOrganizer","SubmissionAuthor","SubmissionUser","Registration","Assignment"]
    chosenFunctions = []
    
    #Get options from model
    for component in smart_home_model.components:
        if component.__class__.__name__ == 'Action':
            actionsArray.append(component.function)    
        elif component.__class__.__name__ == 'Device':
            devicesArray.append(component)
            componentDict[component.name] = {"device":component}
            if not component.typeDevice in componentDictByBaseDevice:
                componentDictByBaseDevice[component.typeDevice] = {}
            componentDictByBaseDevice[component.typeDevice][component.name] = {"device":component}
        elif component.__class__.__name__ == 'Feature':
            componentFeatureDict[component.name] = {"feature":component}
       
    for key,value in componentDict.items():
        componentData = ""
        componentExtraData = ""
        if True:
            generateFile(templateFolder,devicesFolder,'deviceTemplate.java',key,jinja_env,value,"",systemName,".java")
       
    #validation
    for keyF, feature in componentFeatureDict.items():
        if not feature["feature"].actuador:
            print("[error] Feature '%s' missing actuator" % (feature["feature"].name)) 
        if feature["feature"].type == "Alternative" and not feature["feature"].alternatives:
            print("[error] Missing alternatives features of '%s' " % (feature["feature"].name)) 
    
    if "PanicMode" not in componentFeatureDict: 
        print("[error] Missing mandatory features: PanicMode") 
    if "PresenceIlusion" not in componentFeatureDict: 
        print("[error] Missing mandatory features: PresenceIlusion") 
    if "UserIlumination" not in componentFeatureDict: 
        print("[error] Missing mandatory features: UserIlumination") 
    
    #code generation       
    generateFile(arduinoCodeFolder,arduinoFolder,'ArduinoControl.java',"ArduinoControl",jinja_env,"",systemPin,systemName,".java") 
    
    for keyN, nameFile in enumerate(avaliableBaseDevices):
        copyCodeFile(devicesCodeFolder,devicesFolder,nameFile,jinja_env,value,componentExtraData,systemName)
        if nameFile != "Hardware": 
            generateFile(templateFolder,enumsFolder,'baseDeviceEnumTemplate.java',nameFile+"Enum",jinja_env,componentDictByBaseDevice[nameFile],nameFile,systemName,".java")
    
    generateCodeRecursively(featureModelingCodeFolder,featureModelingFolder,jinja_env,componentFeatureDict,{},{},systemName, "", "")
    generateFile(templateFolder,featureModelingFolder,'featureHelperTemplate.java',"FeatureHelper",jinja_env,componentFeatureDict,"",systemName,".java")
    
    for keyF, feature in componentFeatureDict.items():
        if feature["feature"].sensor:
            generateFile(templateFolder,featuresFolder,"automatedFeatureTemplate.java",keyF,jinja_env,feature,"",systemName,".java") 
            generateFile(templateFolder,uiFolder,"automatedFeatureUITemplate.java",keyF+"UI",jinja_env,feature,componentExtraData,systemName)
        else:
            if keyF not in ["PresenceIlusion","PanicMode"]:
                generateFile(templateFolder,uiFolder,"listFeatureUITemplate.java",keyF,jinja_env,feature,"",systemName,".java")             
            else:
                copyCodeFile(uiCodeFolder,uiFolder,keyF+"UI",jinja_env,feature,componentExtraData,systemName)
 
            copyCodeFile(featuresCodeFolder,featuresFolder,keyF,jinja_env,feature,componentExtraData,systemName)        
    
    copyCodeFile(uiCodeFolder,uiFolder,"FeatureUIBase",jinja_env,"","",systemName)
    copyCodeFile(homeCodeFolder,homeFolder,'AutomatedFeaturesRunnable',jinja_env,componentFeatureDict,componentExtraData,systemName)
    copyCodeFile(homeCodeFolder,homeFolder,'HouseFacade',jinja_env,componentFeatureDict,componentDict,systemName)
    
    generateCodeRecursively(guiCodeFolder,guiFolder,jinja_env,componentFeatureDict,componentDict,{},systemName, "", "")
    
    generateCodeRecursively(utilCodeFolder,utilFolder,jinja_env,componentDict,{},{},systemName, "", "")
   
def append(data):
    newA = []
    for k,v in data.items():
        if v["feature"].extend and v["feature"].extend.name not in newA:
            newA = newA + [v["feature"].extend.name]   
    return newA

def remove(data,item):
    return data.remove(item)


def upperfirst(x):
    return x[0].upper()+x[1:]

def splitName(fullName):
    return re.sub('([a-z])([A-Z])',"\g<1> \g<2>",fullName)

def split(fullName):
    return fullName.replace(" ","_")

def createDotFiles(smart_home_mm,smart_home_model,dotFolder):
    # Export to .dot file for visualization
    metamodel_export(smart_home_mm, join(dotFolder, 'event_meta.dot'))
    # Export to .dot file for visualization
    model_export(smart_home_model, join(dotFolder, 'event.dot'))

    (graph,) = pydot.graph_from_dot_file(join(dotFolder,'event_meta.dot'))
    graph.write_png(join(dotFolder,'event_meta.png'))
    
    (graph,) = pydot.graph_from_dot_file(join(dotFolder,'event.dot'))
    graph.write_png(join(dotFolder,'event.png'))
    
def copyCodeFile(src,dest,nameFile,jinja_env,var,extraVar,systemName):
    codeFileTemplate = jinja_env.get_template(join(src,nameFile+'.java'))
    with open(join(dest,"%s.java" % nameFile), 'w') as f:
            f.write(codeFileTemplate.render(data=var,extraData=extraVar,systemName=systemName))

def generateFile(src,dest,file,nameFile,jinja_env,var,extraVar,systemName,ext=".java"):
    codeFileTemplate = jinja_env.get_template(join(src,file))
    with open(join(dest,"%s" % nameFile+ext), 'w') as f:
            f.write(codeFileTemplate.render(data=var,extraData=extraVar,systemName=systemName))

def createFolder(dest,name):
    folder = join(dest, name)
    if not exists(folder):
        mkdir(folder)

    return folder

def generateCodeRecursively(src, dest,jinja_env,var,extraVar,statments,systemName,systemEmail,systemPass):
    for (dirpath,dirnames,filenames) in walk (src):
        for file in filenames:
            if file != "FeatureHelper.java":
                codeFileTemplate = jinja_env.get_template(join(src,file))
                with open(join(dest,file), 'w') as f:
                    f.write(codeFileTemplate.render(data=var,extraData=extraVar,statments=statments,systemName=systemName,systemEmail=systemEmail,systemPassword=systemPass))

def copy(src, dest):
    if exists(dest):
        shutil.rmtree(dest)

    try:
        shutil.copytree(src, dest)
    except OSError as e:
        # If the error was caused because the source wasn't a directory
        if e.errno == errno.ENOTDIR:
            shutil.copy(src, dest)
        else:
            print('Directory not copied. Error: %s' % e)

if __name__ == "__main__":
    main()
