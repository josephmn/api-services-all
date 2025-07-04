package com.services.api.all.util;

import static com.services.api.all.util.Constant.POSITIOON_DEUDA;
import static com.services.api.all.util.Constant.POSITIOON_ESTADO;
import static com.services.api.all.util.Constant.POSITIOON_FACTURACIION;
import static com.services.api.all.util.Constant.POSITIOON_NUMERO;
import static com.services.api.all.util.Constant.POSITIOON_PERIODO;
import static com.services.api.all.util.Constant.POSITIOON_SERIE;
import static com.services.api.all.util.Constant.POSITIOON_URL_RECIBO;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.services.api.all.dto.WaterAccountStatementResponse;

/**
 * Class to extract water bill information from HTML.
 *
 * @author Joseph Magallanes
 * @since 2025-07-03
 */
public class HtmlParser {

    /**
     * Extracts water bill information from the provided HTML string.
     *
     * @param html The HTML content as a string.
     * @return A list of WaterAccountStatementResponse objects containing the extracted data.
     */
    public static List<WaterAccountStatementResponse> extractReceipts(String html) {
        final List<WaterAccountStatementResponse> receipts = new ArrayList<>();

        final Document doc = Jsoup.parse(html);

        final Element table = doc.selectFirst("div.card-header:contains(12 ÚLTIMOS RECIBOS FACTURADOS)")
                .parent() // div.card
                .selectFirst("table");

        if (table != null) {
            final Elements rows = table.select("tbody tr");

            for (Element fila : rows) {
                final Elements cells = fila.select("td, th");

                final int numero = Integer.parseInt(cells.get(POSITIOON_NUMERO).text().trim());
                final String facturacion = cells.get(POSITIOON_FACTURACIION).text().trim();
                final String serie = cells.get(POSITIOON_SERIE).text().trim();
                final String periodo = cells.get(POSITIOON_PERIODO).text().trim();
                final String estado = cells.get(POSITIOON_ESTADO).text().trim();
                final String deuda = cells.get(POSITIOON_DEUDA).text().trim();
                final String urlRecibo = cells.get(POSITIOON_URL_RECIBO).selectFirst("a[href]").attr("href");

                receipts.add(new WaterAccountStatementResponse(
                        numero, facturacion, serie, periodo, estado, deuda, urlRecibo));
            }
        }

        return receipts;
    }
}
