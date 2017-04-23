package collpoll;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Solution {
public static double LAT =  12.935076;
public static double LONG =   77.614277;
ArrayList<Customer> customerList = new ArrayList<Customer>();

	public void findCustomers()
	{
		
		JSONParser parser = new JSONParser();
		JSONArray a;
		try 
		{
			a = (JSONArray) parser.parse(new FileReader("/home/vishal/workspace/collpoll/src/collpoll/customers.json"));
			for (Object o : a)
			  {
			    JSONObject person = (JSONObject) o;

			    int id = Integer.parseInt((String)(person.get("id")));
			    //System.out.println(id);

			    String name = (String) person.get("name");
			    //System.out.println(name);

			    double latitude = Double.parseDouble((String) person.get("latitude"));
			    //System.out.println(latitude);

			    double longitude = Double.parseDouble((String) person.get("longitude"));
			    //System.out.println(longitude);
			    Customer c = new Customer();
			    c.id=id;
			    c.name=name;
			    c.distance=greatCircleDistance(latitude, longitude, LAT, LONG);
			    addToList(c);
			  }
			for(Customer c:customerList)
			{
				System.out.println(c);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
	public void addToList(Customer c)
	{
		if(c.distance>100)
			return;
		int pos = binarySearch(0, customerList.size()-1, c.id);
		//System.out.println(pos);
		if(pos==-1)
			customerList.add(c);
		else
			customerList.add(pos, c);
		
		//System.out.println(customerList);
	}
	int binarySearch( int l, int r, int x)
    {//Collections.
        if (r>=l)
        {
            int mid = l + (r - l)/2;
 
            // If the element is present at the middle itself
            if (customerList.get(mid).id == x)
               return mid;
 
            // If element is smaller than mid, then it can only
            // be present in left subarray
            if (customerList.get(mid).id > x)
               return binarySearch(l, mid-1, x);
 
            // Else the element can only be present in right
            // subarray
            return binarySearch(mid+1, r, x);
        }
 
        // We reach here when element is not present in array
        return l;
    }
	public double greatCircleDistance(double lat1,double long1, double lat2, double long2)
	{
		 double x1 = Math.toRadians(lat1);
	        double y1 = Math.toRadians(long1);
	        double x2 = Math.toRadians(lat2);
	        double y2 = Math.toRadians(long2);

	       /*************************************************************************
	        * Compute using law of cosines
	        *************************************************************************/
	        // great circle distance in radians
	        double angle1 = Math.acos(Math.sin(x1) * Math.sin(x2)
	                      + Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));

	        // convert back to degrees
	        angle1 = Math.toDegrees(angle1);

	        // each degree on a great circle of Earth is 60 nautical miles
	        double distanceNMI = angle1*60;
	        double distanceKM = distanceNMI*1.852;

	        //System.out.println(distanceKM + " KM");
	        return distanceKM;
	}
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		new Solution().findCustomers();
	}

}
class Customer
{
	String name;
	int id;
	double distance;
	
	@Override
	public String toString() 
	{
		return "id= "+id+" name="+name+" distance="+distance;
	}		
}