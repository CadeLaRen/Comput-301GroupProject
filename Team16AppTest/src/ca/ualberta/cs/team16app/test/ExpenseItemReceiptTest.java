package ca.ualberta.cs.team16app.test;

import junit.framework.TestCase;


public class ExpenseItemReceiptTest extends TestCase
{
	//Chris Lin
		//view receipt for an expense item
		//US06.02.01
		public void viewReceiptTest(){
			Item photo = new Item("photo");
			assertTrue("Photo exist!!",photo.getPhoto() != null);
		}
		
		
		
		//qtan
		//US06.04.01
		public class Sizeoverlimit {
			//case 06-0401
			//make sure the size of the image file is below 65536 bytes
			public void testLargeBitmap(){
				Bitmap img = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888 );
				
				Claim claim = new Claim("test", new Date(111), new Date(222));
				
				claim.addReceipt(img);
				Bitmap result = claim.getReceipt();
				if(result.getByteCount() > 65536){
					fail("over the limit");
				}
				assertFalse("", img == result);
			}
		}

}