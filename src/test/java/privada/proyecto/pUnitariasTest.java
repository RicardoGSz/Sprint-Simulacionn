package privada.proyecto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class pUnitariasTest {

    @BeforeEach
    public void resetEstado() {
        try {
            java.lang.reflect.Field intentos = loginTestTest.class.getDeclaredField("intentosFallidos");
            intentos.setAccessible(true);
            ((java.util.Map<?, ?>) intentos.get(null)).clear();
        } catch (Exception e) {
            fail("Error al reiniciar el estado de la clase loginTest");
        }
    }

    @Test
    public void testInicioSesionExitoso() {
        assertTrue(loginTest.iniciarSesion("admin", "1234"));
    }

    @Test
    public void testUsuarioInexistente() {
        assertFalse(loginTest.iniciarSesion("noExiste", "1234"));
        assertEquals(1, loginTest.getIntentosFallidos("noExiste"));
    }

    @Test
    public void testContraseñaIncorrecta() {
        assertFalse(loginTest.iniciarSesion("admin", "wrongpass"));
        assertEquals(1, loginTest.getIntentosFallidos("admin"));
    }

    @Test
    public void testUsuarioNulo() {
        assertFalse(loginTest.iniciarSesion(null, "1234"));
    }

    @Test
    public void testContraseñaNula() {
        assertFalse(loginTest.iniciarSesion("admin", null));
    }

    @Test
    public void testUsuarioConEspacios() {
        assertFalse(loginTest.iniciarSesion("   ", "1234"));
    }

    @Test
    public void testContraseñaVacia() {
        assertFalse(loginTest.iniciarSesion("admin", ""));
    }

    @Test
    public void testUsuarioVacio() {
        assertFalse(loginTest.iniciarSesion("", "1234"));
    }

    @Test
    public void testMayusculasMinusculas() {
        assertFalse(loginTest.iniciarSesion("Admin", "1234")); 
    }

    @Test
    public void testUsuarioConMayusculasValido() {
        assertTrue(loginTest.iniciarSesion("UsuarioMayus", "Clave"));
    }

    @Test
    public void testUsuarioBloqueado() {
        assertFalse(loginTest.iniciarSesion("bloqueado", "0000"));
    }

    @Test
    public void testUsuarioCaracteresEspeciales() {
        assertTrue(loginTest.iniciarSesion("usuarioEspecial", "pass@2025"));
    }

    @Test
    public void testMultiplesIntentosFallidos() {
        loginTest.iniciarSesion("admin", "wrong1");
        loginTest.iniciarSesion("admin", "wrong2");
        loginTest.iniciarSesion("admin", "wrong3");
        loginTest.iniciarSesion("admin", "wrong4");
        assertEquals(3, loginTest.getIntentosFallidos("admin"));
    }
}
