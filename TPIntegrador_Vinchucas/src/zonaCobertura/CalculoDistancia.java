package zonaCobertura;

public class CalculoDistancia implements CalculadorDistancia {

    private static final double radioTierraKm = 6371;

    @Override
    public double calcular(Ubicacion origen, Ubicacion destino) {
        double lat1 = Math.toRadians(origen.getLatitud());
        double lon1 = Math.toRadians(origen.getLongitud());
        double lat2 = Math.toRadians(destino.getLatitud());
        double lon2 = Math.toRadians(destino.getLongitud());

        double deltaLat = lat2 - lat1;
        double deltaLon = lon2 - lon1;

        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);


        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radioTierraKm * c;
    }
}