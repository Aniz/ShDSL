package rise.smarthome.util;

public class ListUtils {
	private ListUtils(){}
	
	public static int[] createArrayRange(int begin, int end){
		int[] ans = new int[end-begin];
		for (int i = begin,j = 0; i < end; i++,j++) {
			ans[j] = i;
		}
		return ans;
	}
	
	public static boolean isInRange(int[] array, int number){
		for (int i = 0; i < array.length; i++) {
			if(array[i]==number){
				return true;
			}
		}
		return false;
	}
	
}