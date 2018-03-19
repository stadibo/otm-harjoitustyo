package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(100);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void korttiKontruktoriToimiiOikein() {
        assertEquals("saldo: 1.0", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataSaldoaOikein() {
        kortti.lataaRahaa(155);
        assertEquals("saldo: 2.55", kortti.toString());
    }
    
    @Test
    public void saldoEiVaheneJosOttaaLiikaa() {
        kortti.otaRahaa(150);
        assertEquals("saldo: 1.0", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinKunRahaRiittaa() {
        kortti.otaRahaa(70);
        assertEquals("saldo: 0.30", kortti.toString());
    }
    
    @Test
    public void palauttaaFalseKunSaldoEiVahene() {
        assertEquals(false, kortti.otaRahaa(150));
    }
    
    @Test
    public void palauttaaTrueKunSaldoRiittaa() {
        assertEquals(true, kortti.otaRahaa(70));
    }
    
    @Test
    public void saldoPalauttaaOikeanSaldon() {
        assertEquals(100, kortti.saldo());
    }
    
}
