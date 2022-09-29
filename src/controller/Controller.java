package controller;

import java.time.Clock;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Controller extends Thread{

   private int numIngressos = 100;
   private Semaphore semaforo;

   public Controller(Semaphore semaforo){
       this.semaforo = semaforo;
   }

    @Override
    public void run() {
        login();
    }

    public void login(){
        long time = timer(50, 2000);
        nap(time);
        if(time > 1000){
            System.err.println("Tempo limite de login expirado.");
        }
        else{
            nap(time);
            compra();
        }
    }

    public void compra() {
        long time = timer(1000, 3000);
        nap(time);
        if(time > 2500){
            System.err.println("Tempo limite de compra expirado.");
        }
        else{
            int ordem = compraAleatoria();
            System.out.println("Ordem de compra de " + ordem + " ingressos");
            try {
                semaforo.acquire();
                valida(ordem);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            semaforo.release();
        }
    }

    public void valida(int ordem){
        if(ordem < numIngressos){
            numIngressos = numIngressos - ordem;
            System.out.println("Compra de " + ordem + " feita com sucesso!");
        }
        else{
            System.err.println("Quantidade indisponivel de ingressos. Sessão expirada");
        }
    }

    public long timer(int min, int max){
        Random random = new Random();
        return (long) (random.nextInt(max - min)) + min;
    }

    public int compraAleatoria(){
        Random random = new Random();
        return (random.nextInt(3)) + 1;
    }

    public void nap(long time){
        try {
            sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
