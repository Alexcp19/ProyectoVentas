package proyecto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;

/**
 * Genera los archivos de texto que contienen la información necesaria
 * para el procesamiento de las ventas del proyecto.
 *
 * <p>La clase crea tres tipos de archivos: un archivo con la información
 * de los productos, un archivo con la información de los vendedores y
 * archivos individuales con las ventas realizadas por cada vendedor.</p>
 *
 * <p>Los datos generados son pseudoaleatorios y mantienen la relación
 * entre los identificadores de productos y vendedores utilizados en
 * los diferentes archivos.</p>
 *
 * @author Equipo de desarrollo
 * @version 1.0
 */

public class GenerateInfoFiles {
	
	private static final Random RANDOM = new Random();

    private static final String OUTPUT_DIRECTORY = "archivos";

    private static final String PRODUCTS_FILE = "productos.txt";

    private static final int PRODUCTS_COUNT = 20;
    
    private static final String SALESMEN_INFO_FILE = "vendedores.txt";

    private static final int SALESMEN_COUNT = 10;
    
    private static final int MAX_SALES_PER_FILE = 10;

    private static final String DOCUMENT_TYPE = "CC";

    private static final String[] PRODUCT_NAMES = {
        "Shampoo Reparador",
        "Mascarilla Capilar",
        "Tratamiento de Keratina",
        "Tinte Permanente",
        "Acondicionador Nutritivo",
        "Crema Corporal",
        "Bloqueador Solar",
        "Bronceador",
        "Body Splash",
        "Esmalte de Uñas",
        "Removedor de Esmalte",
        "Tratamiento Restaurador",
        "Aceite Capilar",
        "Crema para Peinar",
        "Tratamiento Anticaída",
        "Tinte Semipermanente",
        "Mascarilla Hidratante",
        "Gel Capilar",
        "Protector Térmico",
        "Tratamiento de Brillo"
    };
    
    private static final String[] FIRST_NAMES = {
    	    "Carlos",
    	    "Laura",
    	    "Andrés",
    	    "Camila",
    	    "Juan",
    	    "María",
    	    "Daniel",
    	    "Sofía",
    	    "Sebastián",
    	    "Valentina"
    	};

    	private static final String[] LAST_NAMES = {
    	    "Gómez",
    	    "Rodríguez",
    	    "Martínez",
    	    "López",
    	    "Hernández",
    	    "García",
    	    "Pérez",
    	    "Ramírez",
    	    "Torres",
    	    "Moreno"
    	};
    	
    	/**
    	 * Punto de entrada de la aplicación.
    	 *
    	 * <p>Crea el directorio de salida y genera los archivos de productos,
    	 * vendedores y ventas utilizando información pseudoaleatoria.</p>
    	 *
    	 * @param args argumentos recibidos desde la línea de comandos.
    	 */
    	
    public static void main(String[] args) {

        try {

            createOutputDirectory();

            createProductsFile(PRODUCTS_COUNT);

            System.out.println(
                "Archivo de productos generado correctamente."
            );
            
            createSalesManInfoFile(SALESMEN_COUNT);

            System.out.println(
                "Archivo de vendedores generado correctamente."
            );
            
            for (int salesmanIndex = 0;
                    salesmanIndex < SALESMEN_COUNT;
                    salesmanIndex++) {

                long documentNumber = 1001 + salesmanIndex;

                createSalesMenFile(
                    MAX_SALES_PER_FILE,
                    "vendedor",
                    documentNumber
                );
            }

            System.out.println(
                "Archivos de ventas generados correctamente."
            );

        } catch (IOException exception) {

            System.out.println(
                "Se produjo un error: "
                + exception.getMessage()
            );
        }
    }
    
    /**
     * Crea el directorio utilizado para almacenar los archivos generados.
     *
     * <p>Si el directorio ya existe, no se realiza ninguna acción.</p>
     *
     * @throws IOException si no es posible crear el directorio de salida.
     */
    
    private static void createOutputDirectory() throws IOException {

        File directory = new File(OUTPUT_DIRECTORY);

        if (!directory.exists()) {

            boolean created = directory.mkdirs();

            if (!created) {
                throw new IOException(
                    "No fue posible crear la carpeta de salida."
                );
            }
        }
    }
    
    /**
     * Genera el archivo que contiene la información de los productos.
     *
     * <p>Cada producto se almacena en una línea utilizando el formato:
     * IDProducto;NombreProducto;PrecioPorUnidadProducto.</p>
     *
     * @param productsCount cantidad de productos que se generarán.
     * @throws IOException si ocurre un error durante la creación o escritura
     *         del archivo.
     */
    
    public static void createProductsFile(int productsCount)
            throws IOException {

        File file = new File(
            OUTPUT_DIRECTORY + File.separator + PRODUCTS_FILE
        );

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {

            for (int productId = 1;
                    productId <= productsCount;
                    productId++) {

                String productName =
                    PRODUCT_NAMES[
                        (productId - 1) % PRODUCT_NAMES.length
                    ];

                double price = generateProductPrice();

                writer.write(
                    productId
                    + ";"
                    + productName
                    + ";"
                    + String.format(Locale.US, "%.2f", price)
                );

                writer.newLine();
            }
        }
    }
    
    /**
     * Genera el archivo con la información básica de los vendedores.
     *
     * <p>Cada vendedor se almacena en una línea utilizando el formato:
     * TipoDocumento;NúmeroDocumento;Nombres;Apellidos.</p>
     *
     * @param salesmanCount cantidad de vendedores que se generarán.
     * @throws IOException si ocurre un error durante la creación o escritura
     *         del archivo.
     */
    
    public static void createSalesManInfoFile(int salesmanCount)
            throws IOException {

        File file = new File(
            OUTPUT_DIRECTORY + File.separator + SALESMEN_INFO_FILE
        );

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {

            for (int salesmanIndex = 0;
                    salesmanIndex < salesmanCount;
                    salesmanIndex++) {

                long documentNumber = 1001 + salesmanIndex;

                String firstName =
                    FIRST_NAMES[salesmanIndex % FIRST_NAMES.length];

                String lastName =
                    LAST_NAMES[salesmanIndex % LAST_NAMES.length];

                writer.write(
                    DOCUMENT_TYPE
                    + ";"
                    + documentNumber
                    + ";"
                    + firstName
                    + ";"
                    + lastName
                );

                writer.newLine();
            }
        }
    }
    
    /**
     * Genera el archivo de ventas correspondiente a un vendedor.
     *
     * <p>El archivo comienza con la identificación del vendedor y
     * posteriormente contiene registros de productos vendidos. Los productos
     * seleccionados son diferentes entre sí dentro del mismo archivo y sus
     * cantidades son generadas de forma pseudoaleatoria.</p>
     *
     * @param randomSalesCount cantidad máxima de registros de productos
     *                         que se pueden generar.
     * @param name nombre utilizado como parte del nombre del archivo.
     * @param id número de documento del vendedor.
     * @throws IOException si ocurre un error durante la creación o escritura
     *         del archivo.
     * @throws IllegalArgumentException si la cantidad máxima de ventas
     *         es menor o igual que cero.
     */
    
    public static void createSalesMenFile(
            int randomSalesCount,
            String name,
            long id) throws IOException {
    	
    	if (randomSalesCount <= 0) {
    	    throw new IllegalArgumentException(
    	        "La cantidad de ventas debe ser mayor que cero."
    	    );
    	}

        File file = new File(
            OUTPUT_DIRECTORY
            + File.separator
            + name
            + "_"
            + id
            + ".txt"
        );

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file))) {

            writer.write(DOCUMENT_TYPE + ";" + id);
            writer.newLine();

            int maximumSales = Math.min(
            	    randomSalesCount,
            	    PRODUCTS_COUNT
            	);

            	int salesCount = 1 + RANDOM.nextInt(maximumSales);

            	boolean[] usedProducts = new boolean[PRODUCTS_COUNT];

            	for (int saleIndex = 0;
            	        saleIndex < salesCount;
            	        saleIndex++) {

            	    int productId;

            	    do {
            	        productId = 1 + RANDOM.nextInt(PRODUCTS_COUNT);
            	    } while (usedProducts[productId - 1]);

            	    usedProducts[productId - 1] = true;

            	    int quantity = 1 + RANDOM.nextInt(10);

            	    writer.write(
            	        productId
            	        + ";"
            	        + quantity
            	        + ";"
            	    );

            	    writer.newLine();
            	}
        }
    }
    
    /**
     * Genera un precio pseudoaleatorio para un producto.
     *
     * <p>El valor generado se encuentra dentro del rango establecido
     * por los precios mínimo y máximo definidos en el método.</p>
     *
     * @return precio pseudoaleatorio del producto.
     */

    private static double generateProductPrice() {

        int minimumPrice = 5000;
        int maximumPrice = 50000;

        return minimumPrice
            + RANDOM.nextInt(maximumPrice - minimumPrice + 1);
    }
}