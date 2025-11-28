package privada.proyecto;
import java.util.*;

public class loginTest {
    private static final Map<String, String> usuarios = new HashMap<>();
    private static final Set<String> bloqueados = new HashSet<>();
    private static final Map<String, Integer> intentosFallidos = new HashMap<>();

    static {
        usuarios.put("admin", "1234");
        usuarios.put("usuarioEspecial", "pass@2025");
        usuarios.put("UsuarioMayus", "Clave");
        usuarios.put("bloqueado", "0000");
        bloqueados.add("bloqueado");
    }

    public static boolean iniciarSesion(String usuario, String contraseña) {
        if (usuario == null || contraseña == null) return false;
        if (usuario.trim().isEmpty() || contraseña.isEmpty()) return false;
        if (bloqueados.contains(usuario)) return false;

        String claveReal = usuarios.get(usuario);
        boolean valido = contraseña.equals(claveReal);

        if (!valido) {
            intentosFallidos.put(usuario, intentosFallidos.getOrDefault(usuario, 0) + 1);
        }

        return valido;
    }

    public static int getIntentosFallidos(String usuario) {
        return intentosFallidos.getOrDefault(usuario, 0);
    }
}
