package view;

import controller.Controller;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Controller[] controllers = new Controller[300];
        Semaphore semaforo = new Semaphore(1);

        for(Controller controller : controllers){
            controller = new Controller(semaforo);
            controller.start();
        }
    }
}
