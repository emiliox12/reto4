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
		String name = "";
		String country = "";
		String n = "";
		String tag = "";
		String res = "";

		while (!fin) {
			view.printMenu();

			int option = lector.nextInt();
			switch (option) {
			case 1:
				view.printMessage("--------- \nLandingPoint name");
				name = lector.next();
				res = modelo.req1(name);
				view.printMessage(res);
				break;
//			case 2:
//				view.printMessage("--------- \nVideo Trending de país: \nnombre del país?");
//				country = lector.next();
//				System.out.println(country);
//				res = modelo.req2(country);
//				view.printMessage(res);
//				break;
//
//			case 3:
//				view.printMessage("--------- \nVideo Trending por categoría: \nnombre de la categoría?");
//				name = lector.next();
//				System.out.println(name);
//				res = modelo.req3(name);
//				view.printMessage(res);
//				break;
//
//			case 4:
//				view.printMessage("--------- \nEtiqueta del video?");
//				tag = lector.next();
//				view.printMessage("--------- \nnumero de registros?");
//				n = lector.next();
//				System.out.println(country + " " + " " + Integer.parseInt(n) + " " + tag);
//				res = modelo.req4(tag, Integer.parseInt(n));
//				view.printMessage(res);
//				break;

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
