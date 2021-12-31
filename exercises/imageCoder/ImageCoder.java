public class ImageCoder {
	 	public static void main(String[] args){
			Image image = new Image("testImage.jpg");
			encrypt(image, "Hello World");
			decrypt(image, "Hello World");
			image.display();
		}

		//Draws rectangle on given image
		private static void addRectangle(Image image, int x, int y, int width, int height, int red, int green, int blue) {
			for(int i = y; i < image.height() && i-y < height; i++)
				for(int j = x; j < image.width() && j-x < width; j++)
					image.setPixel(j,i,red,green,blue);	
		}

		//Draws circle on given image
		private static void addCircle(Image image, int x, int y, int radius, int red, int green, int blue) {
			for(int i = -y; i < image.height() && i-y < radius; i++)
				for(int j = -x; j < image.width() && j-x < radius; j++)
					if(Math.pow(j-x,2)+Math.pow(i-y,2)<Math.pow(radius,2))
						image.setPixel(j,i,red,green,blue);	
		}
	
		//Encrypt image by adding last pixel and ascii character to pixel color value
		private static void encrypt(Image image, String key) {
			int[] lastPixel = new int[]{0,0,0};
			for(int i = 0; i < image.height(); i++)
				for(int j = 0; j < image.width(); j++){
					for(int q = 0; q < 3; q++) 
						lastPixel[q] = lastPixel[q] + (int)(key.charAt((i*image.width()+j)%key.length()));
					image.setPixel(j,i,(lastPixel[0]+image.red(j,i))%256,(lastPixel[1]+image.green(j,i))%256,(lastPixel[2]+image.blue(j,i))%256);
					lastPixel[0]=image.red(j,i);
					lastPixel[1]=image.green(j,i);
					lastPixel[2]=image.blue(j,i);
					}
		}	

		//Decrypt image from encrypt method
		private static void decrypt(Image image, String key) {
			for(int i = image.height-1; -1 != i; i--)
				for(int j = image.width()-1; -1 != j; j--){
					//Get last pixel
					int[] lastCoordinate = new int[2];
					if (j == 0) {
						lastCoordinate[1] = i - 1;
						lastCoordinate[0] = image.width()-1;
					}
					else {
						lastCoordinate[1] = i;
						lastCoordinate[0] = j-1;
					}
					int[] lastPixel = new int[3];
					if (i==0 && j==0) 
						lastPixel = new int[]{0,0,0};
					else {
						lastPixel[0] = image.red(lastCoordinate[0],lastCoordinate[1]);
						lastPixel[1] = image.green(lastCoordinate[0],lastCoordinate[1]);
						lastPixel[2] = image.blue(lastCoordinate[0],lastCoordinate[1]);
					}
					//Modify current pixel
					int[] pixel = new int[3];
					pixel[0]=image.red(j,i);
					pixel[1]=image.green(j,i);
					pixel[2]=image.blue(j,i);
					for(int q = 0; q < 3; q++) {
						lastPixel[q] = lastPixel[q]+(int)(key.charAt((i*image.width()+j)%key.length()));	
					pixel[q] = pixel[q] - lastPixel[q];
						while(pixel[q] < 0)
							pixel[q] = pixel[q] + 256;
					}
					image.setPixel(j,i,pixel[0],pixel[1],pixel[2]);
				}
		}
}
