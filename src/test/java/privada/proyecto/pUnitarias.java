package privada.proyecto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class pUnitarias {

    @BeforeEach
    public void resetEstado() {
        try {
            java.lang.reflect.Field intentos = login.class.getDeclaredField("intentosFallidos");
            intentos.setAccessible(true);
            ((java.util.Map<?, ?>) intentos.get(null)).clear();
        } catch (Exception e) {
            fail("Error al reiniciar el estado de la clase login");
        }
    }

    @Test
    public void testInicioSesionExitoso() {
        assertTrue(login.iniciarSesion("admin", "1234"));
    }

    @Test
    public void testUsuarioInexistente() {
        assertFalse(login.iniciarSesion("noExiste", "1234"));
        assertEquals(1, login.getIntentosFallidos("noExiste"));
    }

    @Test
    public void testContraseñaIncorrecta() {
        assertFalse(login.iniciarSesion("admin", "wrongpass"));
        assertEquals(1, login.getIntentosFallidos("admin"));
    }

    @Test
    public void testUsuarioNulo() {
        assertFalse(login.iniciarSesion(null, "1234"));
    }

    @Test
    public void testContraseñaNula() {
        assertFalse(login.iniciarSesion("admin", null));
    }

    @Test
    public void testUsuarioConEspacios() {
        assertFalse(login.iniciarSesion("   ", "1234"));
    }

    @Test
    public void testContraseñaVacia() {
        assertFalse(login.iniciarSesion("admin", ""));
    }

    @Test
    public void testUsuarioVacio() {
        assertFalse(login.iniciarSesion("", "1234"));
    }

    @Test
    public void testMayusculasMinusculas() {
        assertFalse(login.iniciarSesion("Admin", "1234")); 
    }

    @Test
    public void testUsuarioConMayusculasValido() {
        assertTrue(login.iniciarSesion("UsuarioMayus", "Clave"));
    }

    @Test
    public void testUsuarioBloqueado() {
        assertFalse(login.iniciarSesion("bloqueado", "0000"));
    }

    @Test
    public void testUsuarioCaracteresEspeciales() {
        assertTrue(login.iniciarSesion("usuarioEspecial", "pass@2025"));
    }

    @Test
    public void testMultiplesIntentosFallidos() {
        login.iniciarSesion("admin", "wrong1");
        login.iniciarSesion("admin", "wrong2");
        login.iniciarSesion("admin", "wrong3");
        login.iniciarSesion("admin", "wrong4");
        assertEquals(3, login.getIntentosFallidos("admin"));
    }
}
