class Conta{
	
	private int value;

	public Conta(){
		this.value = 0;
	}

	public synchronized void deposit(int val){
		this.value += val;
	}

	public synchronized void withdraw(int val) throws NotEnoughFunds{
		
		if(val > this.value)
			throw new NotEnoughFunds();
		
		else
			this.value -= val;
	}

	public int balance(){
		return this.value;

	}
}

public class Banco2 implements Bank{

	private Conta[] contas;

	public Banco2(int N){
		this.contas = new Conta[N];
		for(int i=0; i<N; i++)
			this.contas[i] = new Conta();

	}

	public int numAccounts(){
	return this.contas.length;
	}


	public void deposit(int id, int val) throws InvalidAccount{
		if (id<this.contas.length)
			this.contas[id].deposit(val);
		else
			throw new InvalidAccount();
	}
	
	public void withdraw(int id, int val) throws InvalidAccount, NotEnoughFunds{
		if (id<this.contas.length)
			this.contas[id].withdraw(val);
		else
			throw new InvalidAccount();
	}

    public int totalBalance(int accounts[]) throws InvalidAccount{
    	
    	int total = 0;
    	int contas_len = contas.length;

    	for(int i=0; i<accounts.length;i++)
    		if(accounts[i] <contas_len)
    			total += contas[i].balance();
    		else
    			throw new InvalidAccount();
		
		return total;

    }

    public int totalBalance() throws InvalidAccount{
    	
    	int total = 0;

    	for(int i=0; i<contas.length;i++)
    		total += contas[i].balance();
		
		return total;

    }



    public void transfer(int from, int to, int amount) throws InvalidAccount, NotEnoughFunds{
    	int contas_len = contas.length;
    	if(from >=contas_len || to>=contas.length)
    		throw new InvalidAccount();
    	//System.out.println(from + " " + to + " ");
    	
    	this.withdraw(from,amount);
    	this.deposit(to, amount);
    }


}