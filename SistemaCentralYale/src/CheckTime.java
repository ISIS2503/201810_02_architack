import java.util.TimerTask;

public class CheckTime extends TimerTask {
	
	private long tiempoUltimo; 
	
	private long tiempoActual;
	
	private int healthChecksPerdidos;
	
	private HealthChecks principal;
	
	public CheckTime(HealthChecks pPrincipal) {
		
		healthChecksPerdidos = 0;
		principal = pPrincipal;
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		tiempoUltimo = principal.getTimeLastHealthCheck();
		tiempoActual = principal.getTimeCurrentHealthCheck();
		
		
		long diferenciaTiempo = tiempoActual - tiempoUltimo;
		
		
		System.out.println("Diferencia Tiempo: " + diferenciaTiempo);
		
		if(diferenciaTiempo > HealthChecks.TIME_BETWEEN_HEALTHCHECKS) {
			System.out.println("Health Check del hub perdido.");
			healthChecksPerdidos++;
		}
		else {
			System.out.println("Health Check del hub recibido correctamente");
			healthChecksPerdidos = 0;
			
			
		}
		
		if(healthChecksPerdidos == HealthChecks.LOST_HEALTHCHECKS_FAIL) {
			System.out.println("Se han perdido " + healthChecksPerdidos + " Health Checks del hub");
			System.out.println("HUB FUERA DE LINEA");
			
			String jsonCorreo = principal.createJsonCorreo("HUB FUERA DE LINEA", "Se han perdido la conexión con el hub ubicado en su inmueble.");
			//Envio Correo
			principal.sendRequest(jsonCorreo);
			System.out.println("Se ha enviado el correo notificando el estado del hub.");
			String jsonFallo = principal.createJsonFallo("88888", HealthChecks.TOPICO_MQTT);
			principal.sendFailure(jsonFallo);
		}
		
		principal.setTimeCurrentHealthCheck(System.currentTimeMillis());
		
	}

}
