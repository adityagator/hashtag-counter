package hashtag;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Hashtable;

public class hashtagcounter {

	public static void main(String[] args) throws IOException {
		String input = null;
		String output = null;

		// Accept command line args, throw error in case of zero
		// or more than 2 args/
		try {
			if(args.length == 0) {
				throw new Exception("Please provide an input file");
			} else if(args.length == 1){
				input = args[0];
			} else if(args.length == 2) {
				System.out.println("2 args given");
				input = args[0];
				output = args[1];
			} else {
				throw new Exception("Too many arguments given");
			}
				
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		FibonacciHeap heap = new FibonacciHeap();
		Hashtable<String, Node> ht = new Hashtable<String, Node>();
		OutputStreamWriter fwriter = null;
		FileOutputStream write_stream = null;
		BufferedReader freader = null;
		try {
			freader = new BufferedReader(new FileReader(input));
			if(output != null) {
				File output_file = new File(output);
				write_stream = new FileOutputStream(output_file);
				fwriter = new OutputStreamWriter(write_stream, "UTF-8");
			}
			while(true) {
				String line = freader.readLine();
				// stopping condition
				if(line.equalsIgnoreCase("stop")) {
					break;
				} 
				else if(line.startsWith("#")){
					String[] hashtagWithCount = line.split(" ");
					String hashtag = hashtagWithCount[0].substring(1);
					int count = Integer.parseInt(hashtagWithCount[1]);
					if(!ht.containsKey(hashtag)) {
						Node node = new Node(hashtag, count);
						ht.put(hashtag, node);
						heap.insert(node, count);
					} else {
						Node node = ht.get(hashtag);
						heap.increaseKey(node, count);
					}
				} 
				else {
					int query = Integer.parseInt(line);
					Node[] removedNodes = new Node[query];
					for(int i = 0; i < query; i++) {
						removedNodes[i] = new Node(heap.getMaxNode().getHashtag(), heap.getMaxNode().getCount());
						if(output != null) 
							fwriter.append(heap.getMaxNode().getHashtag());
						else
							System.out.print(heap.getMaxNode().getHashtag());
						if(i < query - 1) {
							if(output != null)
								fwriter.append(",");
							else
								System.out.print(",");
						} else {
							if(output != null)
								fwriter.append("\n");
							else
								System.out.println("");
						}
						ht.remove(heap.getMaxNode().getHashtag());
						heap.extractMax();
					}
					for(int i = 0; i < query; i++) {
						ht.put(removedNodes[i].getHashtag(), removedNodes[i]);
						heap.insert(removedNodes[i], removedNodes[i].getCount());
					}
				}
				
//				freader.close();
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(freader != null)
				freader.close();
			if(fwriter != null)
				fwriter.close();
			if(write_stream != null) {
				write_stream.close();
			}
			
		}
		
		

	}

}
