import java.lang.Math;
import java.util.ArrayList;

public class Hashtable
{
	private class HashNode
	{
		String key;
		String value;
		HashNode next;

		public HashNode(String key, String value)
		{
			this.key=key;
			this.value=value;
			next=null;
		}
	}

	ArrayList<HashNode> bucket;
	int numBuckets;
	int size;

	public Hashtable()
	{
		bucket = new ArrayList<HashNode>();
		numBuckets =  10000;
		size = 0;

		for (int i=0; i < numBuckets; i++)
			bucket.add(null);
	}

	private int getBucketIndex(String key)
	{
		return (Math.abs(key.hashCode()) % numBuckets);
	}

	public boolean containsKey(String key)
	{
		if (get(key)==null)
			return false;
		return true;
	}

	public String get(String key)
	{
		int index = getBucketIndex(key);
		HashNode head = bucket.get(index);

		while(head!=null)
		{
			if((head.key).equals(key))
			{
				return head.value;
			}
			head=head.next;
		}

		return null;
	}//get

	public void put(String key, String value)
	{
		int index = getBucketIndex(key);
		HashNode head = bucket.get(index);

		while (head != null)
		{
			if (head.key.equals(key))
			{
				head.value = value;
				return;
			}
			head = head.next;
		}

		size++;
		head = bucket.get(index);
		HashNode newNode = new HashNode(key, value);
		newNode.next=head;
		bucket.set(index, newNode);

		if ((1.0*size)/numBuckets >= 0.75)
		{
			ArrayList<HashNode> temp = bucket;
			numBuckets = 2 * numBuckets; 
            bucket = new ArrayList<HashNode>(numBuckets); 
            size = 0; 
            for (int i = 0; i < numBuckets; i++) 
                bucket.add(null); 
  
            for (HashNode headNode : temp) 
            { 
                while (headNode != null) 
                { 
                    put(headNode.key, headNode.value); 
                    headNode = headNode.next; 
                } 
            } 
		}
	}//put

	public String remove(String key) throws Exception
	{
		int index = getBucketIndex(key);
		HashNode head = bucket.get(index);

		HashNode prev = null;
		while (head != null && head.key.equals(key)==false)
		{
			prev = head;
			head = head.next;
		}

		if (head == null)
			throw new Exception();

		size--;

		if (prev != null)
			prev.next = head.next;
		else
			bucket.set(index, head.next);
		return head.value;
	}//remove
}