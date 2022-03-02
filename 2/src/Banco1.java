class Conta1{
	
	private int value;

	public Conta1(){
		this.value = 0;
	}

	public void deposit(int val){
		this.value += val;
	}

	public void withdraw(int val) throws NotEnoughFunds{
		
		if(val > this.value)
			throw new NotEnoughFunds();
		
		else
			this.value -= val;
	}

	public int balance(){
		return this.value;

	}
}

public class Banco1 implements Bank{

	private Conta1[] contas;

	public Banco1(int N){
		this.contas = new Conta1[N];
		for(int i=0; i<N; i++)
			this.contas[i] = new Conta1();

	}

	public int numAccounts(){
	return this.contas.length;
	}


	public synchronized void deposit(int id, int val) throws InvalidAccount{
		if (id<this.contas.length)
			this.contas[id].deposit(val);
		else
			throw new InvalidAccount();
	}
	
	public synchronized void withdraw(int id, int val) throws InvalidAccount, NotEnoughFunds{
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
    	
    	this.withdraw(from,amount);
    	this.deposit(to, amount);
    }


}