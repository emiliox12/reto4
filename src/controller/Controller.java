package controller;

import java.util.Date;
import java.util.Scanner;

import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo */
	private Modelo modelo;

	/* Instancia de la Vista */
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * 
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller() {
		view = new View();
		modelo = new Modelo();
	}

	public void run() {
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String a = "";
		String b = "";
		String c = "";
		String d = "";
		String e = "";
		String res = "";

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				view.printMessage("--------- \nLandingPoint name1");
				a = lector.next();
				view.printMessage("--------- \nLandingPoint name2");
				b = lector.next();
				res = modelo.req1(a, b);
				view.printMessage(res);
				break;
			case 2:
				res = modelo.req2();
				view.printMessage(res);
				break;
			case 3:
				view.printMessage("--------- \nnombre del pais a?");
				a = lector.next();
				view.printMessage("--------- \nnombre del pais b?");
				b = lector.next();
				res = modelo.req3(a,b);
				view.printMessage(res);
				break;

			case 4:
				view.printMessage("--------- \nnombre landing point?");
				a = lector.next();
				res = modelo.req4(a);
				view.printMessage(res);
				break;

			case 5:
				view.printMessage("--------- \n Hasta pronto !! \n---------");
				lector.close();
				fin = true;
				break;

			default:
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}
}
