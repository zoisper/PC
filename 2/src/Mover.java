import java.util.Random;

public class Mover implements Runnable{


	private Bank banco;
	private int moves;
	
	public Mover( Bank banco, int moves){
		this.banco = banco;
		this.moves = moves;
	}

	public void run() {
		Random rand = new Random();
		int num_contas = banco.numAccounts();
		int from, to;
		
		for(int i = 0; i<moves; i++){
			from = rand.nextInt(num_contas);
			while((to = rand.nextInt(num_contas)) == from);
			
			
			try{
				banco.transfer(from,to,1);
			
			}
			catch (InvalidAccount  | NotEnoughFunds e){
				System.out.println(e.getMessage());


			} 

		}


	}

}