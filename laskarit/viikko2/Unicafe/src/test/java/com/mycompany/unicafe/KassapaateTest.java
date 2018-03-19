package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peje
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    Maksukortti huonoKortti;
    
    public KassapaateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
        huonoKortti = new Maksukortti(200);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void kontruktoriAsettaaRahaOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void alussaEiOleMyytyEdullisia() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void alussaEiOleMyytyMaukkaita() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateinenEdullisenOstoKasvattaaKassaa() {
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void kateinenMaukasOstoKasvattaaKassaa() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void kateinenEdullisenOstoVaihtorahaOikein() {
        assertEquals(200, kassa.syoEdullisesti(440));
    }
    
    @Test
    public void kateinenMaukasOstoVaihtorahaOikein() {
        assertEquals(40, kassa.syoMaukkaasti(440));
    }
    
    @Test
    public void kateinenEdullisenOstoLounasMaaraOikein() {
        kassa.syoEdullisesti(240);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateinenMaukasOstoLounasMaaraOikein() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateinenRahaEiRiitaEdullinenKassaOikein() {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kateinenRahaEiRiitaMaukasKassaOikein() {
        kassa.syoMaukkaasti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kateinenRahaEiRiitaEdullinenVaihtorahaOikein() {
        assertEquals(200, kassa.syoEdullisesti(200));
    }
    
    @Test
    public void kateinenRahaEiRiitaMaukasVaihtorahaOikein() {
        assertEquals(200, kassa.syoMaukkaasti(200));
    }
    
    @Test
    public void kateinenRahaEiRiitaEdullinenMyydytMaaraOikein() {
        kassa.syoEdullisesti(200);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateinenRahaEiRiitaMaukasMyydytMaaraOikein() {
        kassa.syoMaukkaasti(200);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiEdullisenOstoOnnistunutOikein() {
        assertEquals(true, kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void korttiMaukasOstoOnnistunutOikein() {
        assertEquals(true, kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void korttiEdullisenOstoOtettuOikein() {
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void korttiMaukasOstoOtettuOikein() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void korttiEdullisenOstoLounasMaaraOikein() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiMaukasOstoLounasMaaraOikein() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiEdullisenOstoKassaEiMuutu() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void korttiMaukasOstoKassaEiMuutu() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void korttiEdullinenRahaEiRiitaKorttiEiMuutu() {
        kassa.syoEdullisesti(huonoKortti);
        assertEquals(200, huonoKortti.saldo());
    }
    
    @Test
    public void korttiMaukasRahaEiRiitaKorttiEiMuutu() {
        kassa.syoMaukkaasti(huonoKortti);
        assertEquals(200, huonoKortti.saldo());
    }
    
    @Test
    public void korttiEdullinenRahaEiRiitaMyydytEiMuutu() {
        kassa.syoEdullisesti(huonoKortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiMaukasRahaEiRiitaMyydytEiMuutu() {
        kassa.syoMaukkaasti(huonoKortti);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiEdullisenOstoEpaonnistunutOikein() {
        assertEquals(false, kassa.syoEdullisesti(huonoKortti));
    }
    
    @Test
    public void korttiMaukasOstoEpaonnistunutOikein() {
        assertEquals(false, kassa.syoMaukkaasti(huonoKortti));
    }
    
    @Test
    public void korttiLataaRahaaOikein() {
        kassa.lataaRahaaKortille(kortti, 200);
        assertEquals(1200, kortti.saldo());
    }
    
    @Test
    public void korttiLataaRahaaKassaKasvaa() {
        kassa.lataaRahaaKortille(kortti, 200);
        assertEquals(100200, kassa.kassassaRahaa());
    }
    
    @Test
    public void korttiLataaRahaaVaarin() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void korttiLataaRahaaKassaEiKasva() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
}
