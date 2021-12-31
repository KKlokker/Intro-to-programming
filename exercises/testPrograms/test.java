public class test {
	 	public static void main(String[] args){
			//System.out.println(atDigit(12345, -1));
			//System.out.println(firstDigit(21415));
			//System.out.println(isPalindrome(1232));
			int[] v = new int[]{1,2,4,5,5,6,12};
			int[] w = new int[]{2,5,6,7,8,9,10,12};
			//int[] q = shuffle(v,w);
			//for(int n: q)
			//	System.out.println(n);
			//int[] v2 = new int[]{4,5,5,6};
			//System.out.println(subsetNotOptimized(v,v2));
			Image test = new Image("testImage.jpg");
			switchColors(test, 0,2);
			test.display();
			Image blur = resample(test);
			blur.display();


		}

		private static void switchColors(Image image, int color1, int color2) {
			for(int i = 0; i<image.height; i++)
				for(int j = 0; j< image.width;j++){
					int[] pixel = new int[3];
					pixel[0] = image.red(j,i);
					pixel[1] = image.green(j,i);
					pixel[2] = image.blue(j,i);
					int temp = pixel[color2];
					pixel[color2]=pixel[color1];
					pixel[color1]=temp;
					image.setPixel(j,i,pixel[0],pixel[1],pixel[2]);
				}
		}

		private static Image resample(Image image) { 
			Image blur = new Image(image.width,image.height);
			for(int i = 0; i<image.height; i++)
				for(int j = 0; j < image.width; j++) {
					int[] average = new int[]{0,0,0};
					int count = 0;
					for(int x = -1; x < 2; x++)
						for(int y = -1; y < 2; y++)
							if(j+x>=0 && j+x < image.width && i+y >= 0 && y+i < image.height) {
								average[0] = average[0]+image.red(j+x,i+y);
								average[1] = average[1]+image.green(j+x,i+y);
								average[2] = average[2]+image.blue(j+x,i+y);
								count++;
							}
					blur.setPixel(j,i,average[0]/count,average[1]/count,average[2]/count);
				}
			return blur;
		}

		private static int firstDigit(int n) {
			int i = 1;
			while (n / i > 10) {
				i = i * 10;
			}
			return (n/i);
		}

		private static int pow(int n,int k) {
			int exponent = k;
			int result = 1;
			while (exponent > 0) {
				result *= n;
				exponent -= 1;
			}
			return result;
		}
		private static int numberLength(int n) {
			int i = 0;
			while(n/pow(10,i) > 10) {
				i += 1;
			}
			return i;
		}

		private static int atDigit(int n, int k) {
			int length = numberLength(n);
			int lower = pow(10,length-k);
			int upper = lower*10;
			int upperDigits = n/upper*upper;
			int lowerDigits = n-n/lower*lower;
			int digit = n-upperDigits-lowerDigits;
			return digit/lower;
		}

		private static boolean isPalindrome(int n) {
			int length = numberLength(n);
			int i = 0;
			while (i < length/2) {
				if (atDigit(n,i) != atDigit(n,length-i)) 
					return false;
				i += 1;
			}
			return true;
		}

		private static boolean setEqual(int[] v, int[] w) {
			boolean equal = true;
			int i,j;
			i=j=0;
			while(i<v.length && equal) {
				boolean found = false;
				j = 0;
				while(j<w.length && equal && !found) {
					if (v[i]==w[j])
						found = true;
					j++;
				}
				equal = found;
				i++;
			}
			return equal;
		}

		private static int[] sortedJoin(int[] v, int[] w) {
			int[] sortedArray = new int[v.length+w.length];
			int[] array1 = v.clone();
			int[] array2 = w.clone();
			int i =0;
			int j = 0;
			while(array1.length > i && array2.length > j) {
				if (array1[i]<array2[j]){
					sortedArray[i+j]=array1[i];
					i++;
				}
				else {
					sortedArray[i+j]=array2[j];
					j++;
				}
			}
			if (array1.length>i)  
				for(int l = i; array1.length > l; l++) 
					sortedArray[l+j]=array1[l];
			
			else  
				for(int l = j; array2.length > l; l++)
					sortedArray[l+i]=array2[l];
			
			return sortedArray;
		}	

		private static boolean subset(int[] v, int[] w) {
			boolean subset = false;
			int i = 0;
			while(i+w.length<v.length && !subset){
				int j = 0;
				while(v.length > j+i && w.length > j && w[j]==v[j+i] )
					j++;
				if(j==w.length)
					subset=true;
				i++;
			}
			return subset;
		}

		private static boolean subsetNotOptimized(int[] v, int[] w) {
			for(int i=0;i+w.length<v.length; i++) 
				for(int j=0;v.length > j+i && w.length > j && w[j]==v[j+i]; j++)
					if (j+1==w.length)
						return true;
			return false;
		}

		private static void reverse(int[] v) {
			for(int i = 0; i<v.length/2;i++){
				int help = v[i];
				v[i]=v[v.length-i-1];
				v[v.length-i-1]=help;
			}
		}

		private static int[] shuffle(int[] v, int[] w) {
			int[] shuffle = new int[w.length+v.length];
			int i = 0;
			while(i<shuffle.length && i/2<v.length && i/2<w.length) {
				if (i%2==0)
					shuffle[i]=v[i/2];
				else
					shuffle[i]=w[i/2];
				i++;
			}
			if (i/2<v.length)
				for(int j = 0; j+i/2<v.length;j++)
					shuffle[i+j]=v[i/2+j];
			else
				for(int j = 0; j+i/2<w.length;j++)
					shuffle[i+j]=w[i/2+j];
			return shuffle;
		}
}
