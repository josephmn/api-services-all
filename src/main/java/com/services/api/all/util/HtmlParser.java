package com.services.api.all.util;

import com.services.api.all.dto.WaterAccountStatementResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlParser {
    public static List<WaterAccountStatementResponse> extraerRecibos(String html) {
        List<WaterAccountStatementResponse> recibos = new ArrayList<>();

        Document doc = Jsoup.parse(html);

        Element tabla = doc.selectFirst("div.card-header:contains(12 ÚLTIMOS RECIBOS FACTURADOS)")
                .parent() // div.card
                .selectFirst("table");

        if (tabla != null) {
            Elements filas = tabla.select("tbody tr");

            for (Element fila : filas) {
                Elements celdas = fila.select("td, th");

                int numero = Integer.parseInt(celdas.get(0).text().trim());
                String facturacion = celdas.get(1).text().trim();
                String serie = celdas.get(2).text().trim();
                String periodo = celdas.get(3).text().trim();
                String estado = celdas.get(4).text().trim();
                String deuda = celdas.get(5).text().trim();
                String urlRecibo = celdas.get(6).selectFirst("a[href]").attr("href");

                recibos.add(new WaterAccountStatementResponse(numero, facturacion, serie, periodo, estado, deuda, urlRecibo));
            }
        }

        return recibos;
    }
}
