public class Observer implements Runnable{
	private Bank banco;
	private int moves;
	private int saldo_total;

	public Observer( Bank banco, int moves, int saldo_total){
		this.banco = banco;
		this.moves = moves;
		this.saldo_total = saldo_total;
	}



	public void run() {
		try{
			for(int i =0; i<moves; i++){
			int balance = banco.totalBalance();
			if(balance != saldo_total )
				System.out.println("saldo errado: " + balance );

			}
		} catch(InvalidAccount e){}

	}
}