#include<iostream>
#include<Magick++.h>
#include <sstream>

using namespace std;
using namespace Magick;

int main(int arg, char* args[])
{
	Image image(args[1]);
		
	ColorRGB color_flag=image.pixelColor(0,0);
	
	int switches=0;
	for(unsigned i=0;i<image.columns();i++)
	{
		
		if(color_flag!= image.pixelColor(i,0))
		{
			color_flag = image.pixelColor(i,0);
			switches++;
		}
				
	}
	
	stringstream image_size;
	image_size << image.columns()+(switches*3)+6;
	image_size << "x";
	image_size << 15+6;

	
	Image new_image(image_size.str(), "black");
	
	int new_column=3;
	int new_row=3;

	

	for(unsigned i=0; i<15; i++)
	{
		color_flag = image.pixelColor(0,i);
		for(unsigned j=0; j<image.columns(); j++)
		{
			if(color_flag != image.pixelColor(j,i))
			{	
				color_flag = image.pixelColor(j,i);
				new_column=new_column+3;	
			}
			new_image.pixelColor(new_column,i+3,image.pixelColor(j,i));
			
			new_column++;
		}
		new_column=3;
	}


	new_image.write("/home/aurea/Dropbox/image-midiBeatles2_news/"+image.fileName());
	//cout<<switches<<endl;

	return 0;

}

