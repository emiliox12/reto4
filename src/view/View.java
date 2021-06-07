package view;

import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
		public void printMenu()
		{
			System.out.println("1. Encontrar por nombre (req1");
			System.out.println("2. Interconexión a más cables (req2)");
			System.out.println("3. Ruta mínima en distancia (req3)");
			System.out.println("4. Impacto de fallo de landing point (req5)");
			System.out.println("5. cerrar");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		public void printMessage(String mensaje) {
			System.out.println(mensaje);
		}		
		
		public void printModelo(Modelo modelo)
		{
			System.out.println(modelo);
		}
}
