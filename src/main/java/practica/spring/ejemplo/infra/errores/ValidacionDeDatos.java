package practica.spring.ejemplo.infra.errores;

public class ValidacionDeDatos extends RuntimeException {
    public ValidacionDeDatos(String s) {
        //llama al constructor de RuntimeException y almacena el mensaje personalizado
        super(s);
    }
}
