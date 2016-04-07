public class Test
{
	public static void main(String[] args)
	{
		//Tester Github.
		Christian har testet		

		   public void onClick(View v) 
		   {
       			Rect rect1 = new Rect();
        		Rect rect2 = new Rect();
        		rect1.setHeight(5);
        		rect1.setWidth(8);
        		rect1.setX(5);
        		rect1.setY(10);
        		rect2.setHeight(10);
        		rect2.setWidth(10);
       			rect2.setX(12);
        		rect2.setY(14);

        		if (rect1.getX()<rect2.getX()+rect2.getWidth()&&
                		rect1.getX()+rect1.getWidth()>rect2.getX()&&
               			rect1.getY()<rect2.getY()+rect2.getHeight()&&
                		rect1.getHeight()+rect1.getY()>rect2.getY())
        		{
            			((TextView)findViewById(R.id.text)).setText("Collision");
        		}
        		else
        		{
            			((TextView)findViewById(R.id.text)).setText("NoCollsion");
      			}
		  }
		