package util;

public class Misc {
	public static <T> void arraySwap(T[] array, int i, int j) {
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
}
