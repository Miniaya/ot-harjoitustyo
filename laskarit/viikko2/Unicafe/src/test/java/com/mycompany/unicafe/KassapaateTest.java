
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate paate;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void luotuKassapaateOnOlemassa(){
        assertTrue(paate != null);
    }
    
    @Test
    public void alussaOikeaMaaraRahaa(){
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void alussaOikeaMaaraLounaita(){
        assertEquals(0, paate.maukkaitaLounaitaMyyty() + paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void oikeaMaaraVaihtorahaaEdullisesta(){
        assertEquals(60, paate.syoEdullisesti(300));
    }
    
    @Test
    public void oikeaMaaraVaihtorahaaMaukkaasta(){
        assertEquals(100, paate.syoMaukkaasti(500));
    }
    
    @Test
    public void kassanRahamaaraKasvaa(){
        paate.syoEdullisesti(240);
        paate.syoMaukkaasti(400);
        
        assertEquals(100640, paate.kassassaRahaa());
    }
    
    @Test
    public void lounaidenMaaraKasvaa(){
        paate.syoEdullisesti(240);
        paate.syoMaukkaasti(400);
        
        assertEquals(2, paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahamaaraEiMuutu(){
        paate.syoEdullisesti(200);
        paate.syoMaukkaasti(300);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kaikkiPalautetaanEdullisissa(){
        assertEquals(200, paate.syoEdullisesti(200));
    }
    
    @Test
    public void kaikkiPalautetaanMaukkaissa(){
        assertEquals(300, paate.syoMaukkaasti(300));
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiMuutu(){
        paate.syoEdullisesti(200);
        paate.syoMaukkaasti(300);
        
        assertEquals(0, paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassanRahamaaraEiMuutuKortilla(){
        paate.syoMaukkaasti(kortti);
        paate.syoEdullisesti(kortti);
        
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortiltaVeloitetaanOikeaSumma(){
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(360, kortti.saldo());
    }
    
    @Test
    public void edullinenKorttiostoPalauttaaTrue(){
        assertTrue(paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void maukasKorttiostoPalauttaaTrue(){
        assertTrue(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void myytyjenLounaidenMaaraKasvaaKortilla(){
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(2, paate.edullisiaLounaitaMyyty() + paate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void ylimenevaaSummaaEiVeloitetaEdullisissa(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        paate.syoEdullisesti(kortti);
        
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void ylimenevaaSummaaEiVeloitetaMaukkaissa(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiMuutuKortilla(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        paate.syoEdullisesti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertEquals(2, paate.maukkaitaLounaitaMyyty() + paate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void virheellinenKorttiostoPalauttaaFalseEdullisessa(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertFalse(paate.syoEdullisesti(kortti));
    }
    
    @Test
    public void virheellinenKorttiostoPalauttaaFalseMaukkaassa(){
        paate.syoMaukkaasti(kortti);
        paate.syoMaukkaasti(kortti);
        
        assertFalse(paate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kortilleLatausOnnistuu(){
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(2000, kortti.saldo());
    }
    
    @Test
    public void kassaPaatteenRahamaaraKasvaaKortilleLadatessa(){
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(101000, paate.kassassaRahaa());
    }
    
    @Test
    public void negatiivinenLatausEiMuutaKortinSaldoa(){
        paate.lataaRahaaKortille(kortti, -1000);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void negatiivinenLatausEiMuutaKassanSaldoa(){
        paate.lataaRahaaKortille(kortti, -1000);
        assertEquals(100000, paate.kassassaRahaa());
    }
}
