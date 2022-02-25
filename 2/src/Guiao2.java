public class Guiao2{
	public static void main(String[] args) throws InterruptedException, InvalidAccount, NotEnoughFunds{

		long start, finish, timeElapsed;

		System.out.println("* Exercicio 1");
		ex1(100,1000);
		
		System.out.println();
		
		System.out.println("* Exercicio 2");
		start = System.currentTimeMillis();
		ex2(100,10,10000);
		finish = System.currentTimeMillis();
		timeElapsed = finish - start;
		System.out.println("Tempo de execução: " + timeElapsed);
		
		System.out.println();

		System.out.println("* Exercicio 3");
		start = System.currentTimeMillis();
		ex3(100,10,10000);
		finish = System.currentTimeMillis();
		timeElapsed = finish - start;
		System.out.println("Tempo de execução: " + timeElapsed);
		

		
	}

	public static void ex1(int N, int I){
		System.out.println("N:" + N + " I:" + I);
		Thread[] threads = new Thread[N];
		Counter counter = new Counter();
		for (int i =0; i<N; i++)
			threads[i] = new Thread(new Incrementer(counter, I));
		for (int i =0; i<N; i++)
			threads[i].start();
		for (int i =0; i<N; i++)
			try{
				threads[i].join();

			}catch(InterruptedException e){
                e.printStackTrace();
            }
        System.out.println("Contador: " + counter.getValue());
	}

	public static void ex2(int N, int num_contas, int num_movimentos) throws InterruptedException, InvalidAccount, NotEnoughFunds{
		

		Bank banco = new Banco1(num_contas);
		
		Thread[] threads = new Thread[N];
		
		for(int i=0; i<num_contas;i++)
			banco.deposit(i,10000);

		System.out.println("Balanço inicial: " + banco.totalBalance());

		for(int i=0; i<N; i++){
			threads[i] = new Thread(new Mover(banco,num_movimentos));
		}

		for(int i=0; i<N; i++){
			threads[i].start();
		}

		for(int i=0; i<N; i++){
			threads[i].join();
		}
		System.out.println("Balanço final: " + banco.totalBalance());



	}

	public static void ex3(int N, int num_contas, int num_movimentos) throws InterruptedException, InvalidAccount, NotEnoughFunds{
		

		Bank banco = new Banco2(num_contas);
		
		Thread[] threads = new Thread[N];
		
		for(int i=0; i<num_contas;i++)
			banco.deposit(i,10000);

		System.out.println("Balanço inicial: " + banco.totalBalance());

		for(int i=0; i<N; i++){
			threads[i] = new Thread(new Mover(banco,num_movimentos));
		}

		for(int i=0; i<N; i++){
			threads[i].start();
		}

		for(int i=0; i<N; i++){
			threads[i].join();
		}
		System.out.println("Balanço final: " + banco.totalBalance());



	}
}