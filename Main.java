import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import OSPRNG.ExponentialRNG;
import OSPRNG.TriangularRNG;
import OSPRNG.UniformContinuousRNG;
import OSPRNG.UniformDiscreteRNG;

import static java.lang.Math.*;

public class Main {
    public static void main(String[] args) {
        //obsahObdlznika();
        //predajSpecialityKonstantny();
        //predajSpeciality();
        //metoda();

        //zadanie1();
        //zadanie3(); // priklad typu 1. (odhad pravdepodobnosti)
        //zadanie2();
        //zadanie4prve();   // priklad typu 2. (odhad premennej)
        //zadanie4druhe();  // priklad typu 2. (odhad premennej)
        //zadanie4tretie(); // priklad typu 2. (odhad premennej)
        //zadanie5Prve();  // priklad 1. typy (odhad pravdepodobnosti)
        //zadanie5Druhe();  // priklad 1. typy (odhad pravdepodobnosti)
        //zadanie6Prve();  // priklad 1. typy (odhad pravdepodobnosti)
        //zadanie6Druhe();  // priklad 1. typy (odhad pravdepodobnosti)
        //zadanie7();  // priklad typu 3. (vyber najlepsieho vstupneho parametra)
        zadanie8(); // priklad typu 3. (vyber najlepsieho vstupneho parametra)
        //zadanie9(); // priklad typu 2. (odhad premennej)

        //jankovic2021();
        //medovniky();
        //medovnikyDisk();
        //triatlon();
        //triatlonDisk();
        //disk48();
        //disk48Disk();

        //sorting();
        //test();

        //dochodky();
        //biatlonistky();
        //biatlonistkyDisk();

        //znova9();
        znova8();
    }

    public static void znova8()
    {
        final int pocetPokusov = 100000;
        final int rozloha = 300;
        final int pracovnyCas = 20;
        int curPocetKombajnov;
        double ostavaRozloha;

        double kombajn;
        TriangularRNG genKombajn = new TriangularRNG(1.0, 3.0, 3.5);


        for (int i = 5; i < 20; i++)
        {
            curPocetKombajnov = i;
            int pocetUspechov = 0;


            for (int j = 0; j < pocetPokusov; j++)
            {
                ostavaRozloha = rozloha;

                for (int k = 0; k < pracovnyCas; k++)
                {
                    for (int l = 0; l < curPocetKombajnov; l++)
                    {
                        kombajn = genKombajn.sample();
                        ostavaRozloha -= kombajn;
                    }
                }

                if (ostavaRozloha <= 0)
                {
                    pocetUspechov++;
                }
            }

            double prNeuspechu = 100 - (double) pocetUspechov / pocetPokusov * 100;
            System.out.println("Kombajnov " + curPocetKombajnov + " pravdepodobnost neuspechu " + prNeuspechu);
        }
    }
    public static void znova9()
    {
        final int pocetPokusov = 1000000;

        double[] naplnenost = new double[7];
        naplnenost[0] = 27;
        double[] cena = new double[7];
        cena[0] = 500;

        double poklesCeny;
        TriangularRNG genPoklesCeny = new TriangularRNG(1.0, 4.0, 11.0);

        double narastNaplnenosti;
        UniformContinuousRNG genNarastNaplnenosti = new UniformContinuousRNG(5.0, 14.0);

        double[] sucetCien = new double[7];


        for (int i = 0; i < pocetPokusov; i++)
        {
            naplnenost[0] = 27;
            cena[0] = 500;
            boolean prekrocenie = false;

            for (int k = 1; k < 7; k++)
            {
                if (prekrocenie)
                {
                    naplnenost[k] = naplnenost[k - 1];
                    cena[k] = cena[k - 1];
                }
                else
                {
                    narastNaplnenosti = genNarastNaplnenosti.sample();
                    naplnenost[k] = naplnenost[k - 1] + narastNaplnenosti;

                    if (naplnenost[k] >= 75)
                    {
                        prekrocenie = true;
                        cena[k] = cena[k - 1] + (cena[k - 1] / 100 * 30);
                    }
                    else
                    {
                        poklesCeny = genPoklesCeny.sample();
                        cena[k] = cena[k - 1] - (cena[k - 1] / 100 * poklesCeny);
                    }
                }
            }


            for (int k = 0; k < 7; k++)
            {
                sucetCien[k] += cena[k];
            }
        }


        double[] priemerCien = new double[7];

        for (int i = 0; i < 7; i++)
        {
            priemerCien[i] = sucetCien[i] / pocetPokusov;
        }


        for (int i = 0; i < 7; i++)
        {
            System.out.println(priemerCien[i]);
        }
    }

    public static void biatlonistkyDisk()
    {
        int p = 0;

        UniformContinuousRNG genNahoda = new UniformContinuousRNG();
        TriangularRNG genBeh = new TriangularRNG(15.0, 24.0, 30.0);
        double nahoda;

        for(int i = 0; i < 100000; i++)
        {
            int odmena = 0;

            for(int j = 0; j < 75; j++)
            {
                int trafenych_tercov = 0;

                for(int t = 0; t < 10; t++)
                {
                    nahoda = genNahoda.sample();

                    if(nahoda <= 0.95)
                    {
                        trafenych_tercov++;
                    }


                    nahoda = genNahoda.sample();

                    if(nahoda <= 0.8)
                    {
                        trafenych_tercov++;
                    }

                }

                double cas_zabehnutia = genBeh.sample();

                if(cas_zabehnutia < 27 && trafenych_tercov == 20)
                {
                    odmena += 1000;
                }
            }

            if(odmena <= 5000)
            {
                p++;
            }
        }

        double pr = (double) p / 100000 * 100;
        System.out.println("Pr, ze budu pridelene prostriedky stacit je: " + pr);
    }

    public static void biatlonistky()
    {
        final int pocetPokusov = 1000000;
        final int pocetBiatlonistiek = 75;

        int pocetUspechovVJednejReplikacii;
        int pocetUspechov = 0;

        double nahoda;
        double beh;

        UniformContinuousRNG genLah = new UniformContinuousRNG();
        UniformContinuousRNG genStoj = new UniformContinuousRNG();
        TriangularRNG genBeh = new TriangularRNG(15.0, 24.0, 30.0);

        for (int i = 0; i < pocetPokusov; i++)
        {
            pocetUspechovVJednejReplikacii = 0;

            for (int k = 0; k < pocetBiatlonistiek; k++)
            {
                beh = genBeh.sample();
                int pocitadlo = 0;

                if (beh < 27)
                {
                    pocitadlo++;
                }


                for (int j = 0; j < 10; j++)
                {
                    nahoda = genLah.sample();

                    if (nahoda <= 0.95)
                    {
                        pocitadlo++;
                    }
                }


                for (int j = 0; j < 10; j++)
                {
                    nahoda = genStoj.sample();

                    if (nahoda <= 0.80)
                    {
                        pocitadlo++;
                    }
                }

                if (pocitadlo == 21)
                {
                    pocetUspechovVJednejReplikacii++;
                }
            }

            if (pocetUspechovVJednejReplikacii <= 5)
            {
                pocetUspechov++;
            }
        }

        double pravdepodobnost = (double)pocetUspechov / pocetPokusov * 100;
        System.out.println(pravdepodobnost);
    }

    public static void dochodky()
    {
        final int pocetPokusov = 100000;
        final int pocetLudi = 270;

        double nasporene;
        int dlzkaPoberania;
        double zisk;
        int percento = 5;

        double sumaDochodky = 0;
        double sumaZisky = 0;
        double percentualnyZisk;

        int pocetUspechov = 0;

        TriangularRNG genNasporene = new TriangularRNG(40.0, 310.0, 2600.0);
        TriangularRNG genDlzkaPoberania = new TriangularRNG(1.0, 15.0, 50.0);


        for (int i = 0; i < pocetPokusov; i++)
        {
            for (int j = 0; j < pocetLudi; j++)
            {
                nasporene = genNasporene.sample();
                sumaDochodky += nasporene;
                zisk = nasporene;
                dlzkaPoberania = (int)Math.round(genDlzkaPoberania.sample());

                for (int k = 0; k < dlzkaPoberania; k++)
                {
                    zisk -= (nasporene / 100 * percento);
                }

                sumaZisky += zisk;
            }

            double rozdiel = sumaDochodky - sumaZisky;
            double percent15 = sumaDochodky / 100 * 15;

            if (rozdiel > percent15 && sumaZisky > 0)
            {
                pocetUspechov++;
            }
        }

        double percentoUspechov = (double)pocetUspechov / pocetPokusov * 100;
        System.out.println(percentoUspechov);
    }

    public static void disk48()
    {
        final int pocetPokusov = 1000000;
        final int pocetZakaziek = 15;

        double nakladyPoJednej;
        double nakladyPoBaleni;

        int lepsiePoJednej = 0;
        int lepsiePoBaleni = 0;

        double jednaVystaciNa;
        double velkostPlochy;
        double ostavaNatriet;
        double uspech;

        TriangularRNG genJednaVystaciNa = new TriangularRNG(10.0, 11.0, 12.0);
        TriangularRNG genVelkostPlochy = new TriangularRNG(100.0, 110.0, 120.0);
        UniformContinuousRNG genUspech = new UniformContinuousRNG();


        for (int i = 0; i < pocetPokusov; i++)
        {
            nakladyPoJednej = 0;
            nakladyPoBaleni = 9000;
            int mamKusov = 30;

            for (int j = 0; j < pocetZakaziek; j++)
            {
                uspech = genUspech.sample();

                if (uspech < 0.60)
                {
                    velkostPlochy = genVelkostPlochy.sample();
                    ostavaNatriet = velkostPlochy;

                    while (ostavaNatriet > 0)
                    {
                        nakladyPoJednej += 300;

                        ostavaNatriet -= genJednaVystaciNa.sample();
                    }


                    ostavaNatriet = velkostPlochy;

                    while (mamKusov > 0)
                    {
                        if (ostavaNatriet < 0)
                        {
                            break;
                        }

                        ostavaNatriet -= genJednaVystaciNa.sample();
                        mamKusov--;
                    }

                    while (ostavaNatriet > 0)
                    {
                        nakladyPoBaleni += 300;

                        ostavaNatriet -= genJednaVystaciNa.sample();
                    }
                }
            }

            if (nakladyPoBaleni > nakladyPoJednej)
            {
                lepsiePoJednej++;
            }
            else if (nakladyPoBaleni < nakladyPoJednej)
            {
                lepsiePoBaleni++;
            }
        }

        System.out.println("Po jednej: " + lepsiePoJednej);
        System.out.println("Po baleni: " + lepsiePoBaleni);
    }

    public static void triatlonDisk()
    {
        int pocetReplikacii = 10000000;
        int pocetPretekarov = 66;

        TriangularRNG casPlavanieGen = new TriangularRNG(20.0, 32.0, 40.0);
        TriangularRNG casCyklistikaGen = new TriangularRNG(60.0, 70.0, 86.0);
        TriangularRNG casBehGen = new TriangularRNG(36.0, 46.0, 63.0);

        UniformContinuousRNG maDefektGen = new UniformContinuousRNG(0.0, 1.0);
        UniformContinuousRNG defektOpravaGen = new UniformContinuousRNG(0.5, 5.0);

        UniformContinuousRNG maRozviazanieGen = new UniformContinuousRNG(0.0, 1.0);
        UniformContinuousRNG rozviazanieOpravaGen = new UniformContinuousRNG(1.0, 5.0);

        double celkovePercentoKvalifikovanych = 0;
        for (int i = 0; i < pocetReplikacii; i++) {
            int pocetKvalifikovanych = 0;

            for (int j = 0; j < pocetPretekarov; j++) {
                double celkovyCasPretekara = casPlavanieGen.sample() + casCyklistikaGen.sample() + casBehGen.sample();

                int pocetDefektov = 0;
                double defektSample = maDefektGen.sample();
                if (defektSample < 0.07)
                    pocetDefektov = 1;
                else if (defektSample < 0.07 + 0.04)
                    pocetDefektov = 2;
                else if (defektSample < 0.07 + 0.04 + 0.015)
                    pocetDefektov = 3;

                for (int k = 0; k < pocetDefektov; k++)
                    celkovyCasPretekara += defektOpravaGen.sample();

                int pocetRozviazani = 0;
                double rozviazanieSample = maRozviazanieGen.sample();
                if (rozviazanieSample < 0.1)
                    pocetRozviazani = 1;
                else if (rozviazanieSample < 0.1 + 0.045)
                    pocetRozviazani = 2;

                for (int k = 0; k < pocetRozviazani; k++)
                    celkovyCasPretekara += rozviazanieOpravaGen.sample();

                if (celkovyCasPretekara < 140)
                    pocetKvalifikovanych++;
            }

            celkovePercentoKvalifikovanych += (double) pocetKvalifikovanych / pocetPretekarov;
        }

        System.out.println("Z regionálnych pretekov sa na celoslovenské kvalifikovalo: " + (celkovePercentoKvalifikovanych / pocetReplikacii) * 100.0 + "% pretekárov");

    }

    public static void triatlon()
    {
        final int pocetReplikacii = 10000000;
        final int pocetPretekarov = 66;
        double casPretekara;

        double plavanie;
        double cyklistika;
        double beh;
        double nahoda;

        TriangularRNG genPlavanie = new TriangularRNG(20.0, 32.0, 40.0);
        TriangularRNG genCyklistika = new TriangularRNG(60.0, 70.0, 86.0);
        TriangularRNG genBeh = new TriangularRNG(36.0, 46.0, 63.0);

        UniformContinuousRNG genNahodaDefekt = new UniformContinuousRNG();
        UniformContinuousRNG genNahodaSnurky = new UniformContinuousRNG();
        UniformContinuousRNG genDefekt = new UniformContinuousRNG(0.5, 5.0);
        UniformContinuousRNG genSnurky = new UniformContinuousRNG(1.0, 5.0);

        int pocetUspechov = 0;
        double sucetPercent = 0;


        for (int i = 0; i < pocetReplikacii; i++)
        {
            pocetUspechov = 0;

            for (int j = 0; j < pocetPretekarov; j++)
            {
                plavanie = genPlavanie.sample();
                cyklistika = genCyklistika.sample();
                beh = genBeh.sample();
                casPretekara = plavanie + cyklistika + beh;


                nahoda = genNahodaDefekt.sample();

                if (nahoda < 0.07)
                {
                    casPretekara += genDefekt.sample();
                }
                else if (nahoda < 0.11)
                {
                    casPretekara += genDefekt.sample();
                    casPretekara += genDefekt.sample();
                }
                else if (nahoda < 0.125)
                {
                    casPretekara += genDefekt.sample();
                    casPretekara += genDefekt.sample();
                    casPretekara += genDefekt.sample();
                }


                nahoda = genNahodaSnurky.sample();

                if (nahoda < 0.10)
                {
                    casPretekara += genSnurky.sample();
                }
                else if (nahoda < 0.145)
                {
                    casPretekara += genSnurky.sample();
                    casPretekara += genSnurky.sample();
                }


                if (casPretekara < 140)
                {
                    pocetUspechov++;
                }
            }


            double pomerUspechov = (double)pocetUspechov / pocetPretekarov * 100;
            sucetPercent = sucetPercent + pomerUspechov;
        }


        double celkovyPomer = sucetPercent / pocetReplikacii;
        System.out.println(celkovyPomer);
    }

    public static void medovnikyDisk()
    {
        int pocetReplikacii = 10000000;

        int pocetMedovnikov = 100;

        TriangularRNG cenaPredajGen = new TriangularRNG(0.5, 0.75, 1.0);
        TriangularRNG casMedziPredajmi = new TriangularRNG(2.0, 6.0, 10.0);
        UniformContinuousRNG casMedziPredajmiRychlo = new UniformContinuousRNG(1.0, 3.0);

        int celkovoOstalo = 0;
        double celkovyZisk = 0.0;

        for (int i = 0; i < pocetReplikacii; i++) {
            double cenaPredaj = cenaPredajGen.sample();

            int predane = 0;
            double cas = 0.0;
            boolean predavajuSaRychlo = false;
            double ziskZaDen = 0.0;

            while (cas < 8.0 * 60.0) {
                predane++;
                ziskZaDen += cenaPredaj;

                if (predane == pocetMedovnikov)
                    break;

                cas += predavajuSaRychlo ? casMedziPredajmiRychlo.sample() : casMedziPredajmi.sample();

                if (8 * 60 - cas <= 60 && pocetMedovnikov - predane > 10 && !predavajuSaRychlo) {
                    cenaPredaj /= 4.0;
                    predavajuSaRychlo = true;
                }
            }

            celkovoOstalo += pocetMedovnikov - predane;
            celkovyZisk += ziskZaDen;
        }

        System.out.println("Babke priemerne ostáva: " + (double) celkovoOstalo / pocetReplikacii + " med.");
        System.out.println("Babka priemerne za deň utŕži: " + celkovyZisk / pocetReplikacii + " €");
    }

    public static void medovniky()
    {
        final int pocetReplikacii = 1000000;
        final int pracujeMinut = 480;
        final int zakladnyPocet = 100;

        double predajnaCenaZaklad;
        double predajnaCenaZlava;

        double dalsiPredajVCase;

        int ostatokPocet;
        boolean zlava;
        double zisk;


        int sumaZvyskov = 0;
        double sumaZiskov = 0;


        TriangularRNG genCenaZaklad = new TriangularRNG(0.5, 0.75, 1.0);
        TriangularRNG genDalsiPredajVCaseNormal = new TriangularRNG(2.0, 6.0, 10.0);
        UniformContinuousRNG genDalsiPredajVCaseFast = new UniformContinuousRNG(1.0, 3.0);


        for (int i = 0; i < pocetReplikacii; i++)
        {
            zisk = 0;
            ostatokPocet = zakladnyPocet;
            zlava = false;
            predajnaCenaZaklad = genCenaZaklad.sample();
            predajnaCenaZlava = predajnaCenaZaklad / 4;
            dalsiPredajVCase = genDalsiPredajVCaseNormal.sample();


            while (dalsiPredajVCase <= pracujeMinut)
            {
                if (zlava)
                {
                    zisk += predajnaCenaZlava;
                    dalsiPredajVCase = dalsiPredajVCase + genDalsiPredajVCaseFast.sample();
                }
                else
                {
                    zisk += predajnaCenaZaklad;
                    dalsiPredajVCase = dalsiPredajVCase + genDalsiPredajVCaseNormal.sample();
                }


                ostatokPocet--;

                if (ostatokPocet == 0)
                {
                    break;
                }


                if (dalsiPredajVCase >= 420 && ostatokPocet > 10 && !zlava)
                {
                    zlava = true;
                }
            }


            sumaZvyskov += ostatokPocet;
            sumaZiskov += zisk;
        }


        double priemerZvyskov = (double)sumaZvyskov / pocetReplikacii;
        double priemerZiskov = (double)sumaZiskov / pocetReplikacii;

        System.out.println("Priemerne jej ostane " + priemerZvyskov + " medovnikov");
        System.out.println("Priemerne zarobi " + priemerZiskov);
    }

    public static void jankovic2021()
    {
        final int pocetReplikacii = 1000000;
        final int zaciatocnyKapital = 10000;
        final int pocetTyzdnov = 52;
        final int pocetDni = (52 * 7);

        double ostatokPriA;
        double ostatokPriB;

        double zmenaA;
        double zmenaB;

        TriangularRNG genZmenaA = new TriangularRNG(-5.0, 1.4, 3.5);
        UniformContinuousRNG genZmenaB = new UniformContinuousRNG(-2.5, 4.5);

        double sumaOstatkovA = 0;
        double sumaOstatkovB = 0;

        int pocetUspechovA = 0;
        int pocetUspechovB = 0;
        int sucetUspechov = 0;

        double priemernyOstatokA;
        double priemernyOstatokB;


        for (int i = 0; i < pocetReplikacii; i++)
        {
            ostatokPriA = zaciatocnyKapital;
            ostatokPriB = zaciatocnyKapital;


            for (int j = 0; j < pocetDni; j++)
            {
                zmenaA = genZmenaA.sample();
                ostatokPriA = ostatokPriA + (ostatokPriA / 100 * zmenaA);
            }


            for (int j = 0; j < pocetTyzdnov; j++)
            {
                zmenaB = genZmenaB.sample();
                ostatokPriB = ostatokPriB + (ostatokPriB / 100 * zmenaB);
            }


            sumaOstatkovA += ostatokPriA;
            sumaOstatkovB += ostatokPriB;

            if (ostatokPriA > ostatokPriB)
            {
                pocetUspechovA++;
            }
            else if (ostatokPriA < ostatokPriB)
            {
                pocetUspechovB++;
            }
        }


        priemernyOstatokA = sumaOstatkovA / pocetReplikacii;
        priemernyOstatokB = sumaOstatkovB / pocetReplikacii;

        System.out.println("Priemer pri A " + priemernyOstatokA);
        System.out.println("Priemer pri B " + priemernyOstatokB);


        sucetUspechov = pocetUspechovA + pocetUspechovB;

        double lepsieA = (double)pocetUspechovA / sucetUspechov * 100;
        double lepsieB = (double)pocetUspechovB / sucetUspechov * 100;

        System.out.println("Fond A bol vyhodnejsi v " + lepsieA + " percentach pripadov");
        System.out.println("Fond B bol vyhodnejsi v " + lepsieB + " percentach pripadov");
    }

    public static void zadanie1()
    {
        final int pocetPokusov = 10000000;

        final double nakupCena = 0.15;
        final double vykupCena = 0.15 / 100 * 65;
        double predajCena;
        double dlzkaPredaja;
        double dalsiPredajO;

        TriangularRNG genPredajCena = new TriangularRNG(0.25, 0.60, 0.95);
        UniformContinuousRNG genDlzkaPredaja = new UniformContinuousRNG(250.0, 420.0);
        ExponentialRNG genDalsiPredajO = new ExponentialRNG(2.7);

        int pocetBalikov;
        int pocetNovin;

        double zisk;
        double sumaZiskov = 0;
        double priemernyZisk;

        for (int i = 14; i <= 18; i++)
        {
            pocetBalikov = i;
            sumaZiskov = 0;

            for (int j = 0; j < pocetPokusov; j++)
            {
                zisk = 0;
                dalsiPredajO = 0;

                pocetNovin = pocetBalikov * 10;
                predajCena = genPredajCena.sample();
                dlzkaPredaja = genDlzkaPredaja.sample();

                zisk -= pocetNovin * nakupCena;

                while (dalsiPredajO <= dlzkaPredaja)
                {
                    if (pocetNovin == 0)
                    {
                        break;
                    }

                    zisk += predajCena;
                    pocetNovin--;
                    dalsiPredajO += genDalsiPredajO.sample();
                }

                if (pocetNovin > 0)
                {
                    zisk += pocetNovin * vykupCena;
                }

                sumaZiskov += zisk;
            }

            priemernyZisk = sumaZiskov / pocetPokusov;
            System.out.println("Pri " + pocetBalikov + " balikov je zisk " + priemernyZisk);
        }
    }

    public static void zadanie2()
    {
        final int pocetPokusov = 100000000;
        final int pocetKusovA = 70;
        final int pocetKusovB = 90;
        final int predajnaCenaA = 3;
        final int predajnaCenaB = 2;

        double nakladyA;
        double nakladyB;

        int dopytA;
        int dopytB;

        double ziskA;
        double ziskB;

        double celkovyZiskA = 0;
        double celkovyZiskB = 0;

        UniformDiscreteRNG generatorDopytA = new UniformDiscreteRNG(40, 80);
        UniformDiscreteRNG generatorDopytB = new UniformDiscreteRNG(66, 155);

        TriangularRNG generatorNakladyA = new TriangularRNG(1.0, 1.75, 2.5);
        TriangularRNG generatorNakladyB = new TriangularRNG(0.7, 1.2, 1.7);

        for (int i = 0; i < pocetPokusov; i++)
        {
            ziskA = 0;
            ziskB = 0;

            do
            {
                dopytA = generatorDopytA.sample();
            }
            while (dopytA == 80);

            do
            {
                dopytB = generatorDopytB.sample();
            }
            while (dopytB == 155);

            nakladyA = (generatorNakladyA.sample()) * pocetKusovA;
            nakladyB = (generatorNakladyB.sample()) * pocetKusovB;

            if (dopytA >= pocetKusovA)
            {
                ziskA = predajnaCenaA * pocetKusovA - nakladyA;
            }
            else
            {
                ziskA = predajnaCenaA * dopytA - nakladyA;
            }

            if (dopytB >= pocetKusovB)
            {
                ziskB = predajnaCenaB * pocetKusovB - nakladyB;
            }
            else
            {
                ziskB = predajnaCenaB * dopytB - nakladyB;
            }

            celkovyZiskA += ziskA;
            celkovyZiskB += ziskB;
        }

        double priemernyZiskA = (double)celkovyZiskA / pocetPokusov;
        double priemernyZiskB = (double)celkovyZiskB / pocetPokusov;

        System.out.println("Zisk pri predaji vyrobku A " + priemernyZiskA);
        System.out.println("Zisk pri predaji vyrobku B " + priemernyZiskB);
    }

    public static void zadanie3() {
        double x;
        double y;
        double lavaStrana;

        UniformContinuousRNG genX = new UniformContinuousRNG();
        UniformContinuousRNG genY = new UniformContinuousRNG();

        final int pocetPokusov = 1000000;
        int pocetUspechov = 0;
        double odhadPi;

        for (int i = 0; i < pocetPokusov; i++) {
            x = genX.sample();
            y = genY.sample();

            lavaStrana = (Math.pow((x - 0.5), 2) + Math.pow((y - 0.5), 2));

            if (lavaStrana <= 0.25) {
                pocetUspechov++;
            }
        }

        odhadPi = ((double) pocetUspechov / pocetPokusov) / 0.25;
        System.out.println("Odhad pi je " + odhadPi);
    }

    public static void zadanie4prve() {
        int poloha;
        double posun;
        int sumaPoloh = 0;
        double priemernaPoloha;
        final int pocetPokusov = 1000000;
        final int pocetKrokovVJednomPokuse = 1000;

        UniformContinuousRNG genPosun = new UniformContinuousRNG();


        for (int i = 0; i < pocetPokusov; i++) {
            poloha = 0;

            for (int j = 0; j < pocetKrokovVJednomPokuse; j++) {
                posun = genPosun.sample();

                if (posun <= 0.5) {
                    poloha++;
                } else {
                    poloha--;
                }
            }

            sumaPoloh += abs(poloha);
        }

        priemernaPoloha = (double) sumaPoloh / pocetPokusov;

        System.out.println("Priemerna poloha je " + priemernaPoloha);
    }

    public static void zadanie4druhe()
    {
        int polohaX;
        int polohaY;
        double posun;
        int sumaPoloh = 0;
        double priemernaPoloha;
        final int pocetPokusov = 1000000;
        final int pocetKrokovVJednomPokuse = 1000;

        UniformContinuousRNG genPosun = new UniformContinuousRNG();


        for (int i = 0; i < pocetPokusov; i++) {
            polohaX = 0;
            polohaY = 0;

            for (int j = 0; j < pocetKrokovVJednomPokuse; j++) {
                posun = genPosun.sample();

                if (posun <= 0.25) {
                    polohaX++;
                } else if (posun <= 0.50) {
                    polohaX--;
                } else if (posun <= 0.75) {
                    polohaY++;
                } else {
                    polohaY--;
                }
            }

            sumaPoloh += abs(polohaX) + abs(polohaY);
        }

        priemernaPoloha = (double) sumaPoloh / pocetPokusov;

        System.out.println("Priemerna poloha je " + priemernaPoloha);
    }

    public static void zadanie4tretie()
    {
        int polohaX;
        int polohaY;
        int polohaZ;
        double posun;
        int sumaPoloh = 0;
        double priemernaPoloha;
        final int pocetPokusov = 1000000;
        final int pocetKrokovVJednomPokuse = 1000;

        UniformContinuousRNG genPosun = new UniformContinuousRNG();


        for (int i = 0; i < pocetPokusov; i++) {
            polohaX = 0;
            polohaY = 0;
            polohaZ = 0;

            for (int j = 0; j < pocetKrokovVJednomPokuse; j++) {
                posun = genPosun.sample();

                if (posun <= (1.0 / 6)) {
                    polohaX++;
                } else if (posun <= (2.0 / 6)) {
                    polohaX--;
                } else if (posun <= (3.0 / 6)) {
                    polohaY++;
                } else if (posun <= (4.0 / 6)) {
                    polohaY--;
                } else if (posun <= (5.0 / 6)) {
                    polohaZ++;
                } else {
                    polohaZ--;
                }
            }

            sumaPoloh += abs(polohaX) + abs(polohaY) + abs(polohaZ);
        }

        priemernaPoloha = (double) sumaPoloh / pocetPokusov;

        System.out.println("Priemerna poloha je " + priemernaPoloha);
    }

    public static void zadanie5Prve()
    {
        final int pocetPokusov = 1000000;

        double prvaVestica;
        double druhaVestica;
        double tretiaVestica;

        boolean maPravduPrva;
        boolean maPravduDruha;
        boolean maPravduTretia;

        UniformContinuousRNG genPrvaVestica = new UniformContinuousRNG();
        UniformContinuousRNG genDruhaVestica = new UniformContinuousRNG();
        UniformContinuousRNG genTretiaVestica = new UniformContinuousRNG();

        int prveDveRovnako = 0;
        int prveDveSpravne = 0;

        int prveTriRovnako = 0;
        int prveTriSpravne = 0;

        for (int i = 0; i < pocetPokusov; i++)
        {
            maPravduPrva = false;
            maPravduDruha = false;
            maPravduTretia = false;


            prvaVestica = genPrvaVestica.sample();
            druhaVestica = genDruhaVestica.sample();
            tretiaVestica = genTretiaVestica.sample();


            if (prvaVestica < 0.80)
            {
                maPravduPrva = true;
            }

            if (druhaVestica < 0.80)
            {
                maPravduDruha = true;
            }

            if (tretiaVestica < 0.80)
            {
                maPravduTretia = true;
            }


            if (maPravduPrva == maPravduDruha)
            {
                prveDveRovnako++;

                if (maPravduPrva)
                {
                    prveDveSpravne++;
                }
            }


            if (maPravduPrva == maPravduDruha && maPravduDruha == maPravduTretia)
            {
                prveTriRovnako++;

                if (maPravduPrva)
                {
                    prveTriSpravne++;
                }
            }
        }


        double pomerPrveDve = (double)prveDveSpravne / prveDveRovnako * 100;
        double pomerPrveTri = (double)prveTriSpravne / prveTriRovnako * 100;

        System.out.println("Ak prve dve odpovedaju rovnaku maju pravdu v " + pomerPrveDve + " percentach pripadov");
        System.out.println("Ak prve tri odpovedaju rovnaku maju pravdu v " + pomerPrveTri + " percentach pripadov");
    }

    public static void zadanie5Druhe()
    {
        final int pocetPokusov = 1000000;

        double prvaVestica;
        double druhaVestica;
        double tretiaVestica;

        boolean maPravduPrva;
        boolean maPravduDruha;
        boolean maPravduTretia;

        double prahovaHodnotaPrva;
        double prahovaHodnotaDruha;
        double prahovaHodnotaTretia;

        UniformContinuousRNG genPrvaVestica = new UniformContinuousRNG();
        UniformContinuousRNG genDruhaVestica = new UniformContinuousRNG();
        UniformContinuousRNG genTretiaVestica = new UniformContinuousRNG();

        TriangularRNG genPrahovaHodnotaPrva = new TriangularRNG(30.0, 80.0, 100.0);
        TriangularRNG genPrahovaHodnotaDruha = new TriangularRNG(30.0, 80.0, 100.0);
        TriangularRNG genPrahovaHodnotaTretia = new TriangularRNG(30.0, 80.0, 100.0);

        int prveDveRovnako = 0;
        int prveDveSpravne = 0;

        int prveTriRovnako = 0;
        int prveTriSpravne = 0;

        for (int i = 0; i < pocetPokusov; i++)
        {
            maPravduPrva = false;
            maPravduDruha = false;
            maPravduTretia = false;


            prvaVestica = genPrvaVestica.sample();
            druhaVestica = genDruhaVestica.sample();
            tretiaVestica = genTretiaVestica.sample();

            prahovaHodnotaPrva = genPrahovaHodnotaPrva.sample() / 100;
            prahovaHodnotaDruha = genPrahovaHodnotaDruha.sample() / 100;
            prahovaHodnotaTretia = genPrahovaHodnotaTretia.sample() / 100;


            if (prvaVestica < prahovaHodnotaPrva)
            {
                maPravduPrva = true;
            }

            if (druhaVestica < prahovaHodnotaDruha)
            {
                maPravduDruha = true;
            }

            if (tretiaVestica < prahovaHodnotaTretia)
            {
                maPravduTretia = true;
            }


            if (maPravduPrva == maPravduDruha)
            {
                prveDveRovnako++;

                if (maPravduPrva)
                {
                    prveDveSpravne++;
                }
            }


            if (maPravduPrva == maPravduDruha && maPravduDruha == maPravduTretia)
            {
                prveTriRovnako++;

                if (maPravduPrva)
                {
                    prveTriSpravne++;
                }
            }
        }


        double pomerPrveDve = (double)prveDveSpravne / prveDveRovnako * 100;
        double pomerPrveTri = (double)prveTriSpravne / prveTriRovnako * 100;

        System.out.println("Ak prve dve odpovedaju rovnaku maju pravdu v " + pomerPrveDve + " percentach pripadov");
        System.out.println("Ak prve tri odpovedaju rovnaku maju pravdu v " + pomerPrveTri + " percentach pripadov");
    }

    public static void zadanie6Prve()
    {
        final int pocetPokusov = 1000000;
        double cas;

        double[] casy = new double[10];
        String[] auta = new String[10];

        ArrayList<Double> sortedCasy = new ArrayList<>();
        ArrayList<String> sortedAuta = new ArrayList<>();

        double minimumCas;
        String minimumAuto;

        TriangularRNG genFastCas = new TriangularRNG(40.0, 50.0, 75.0);
        TriangularRNG genFuriousCas = new TriangularRNG(35.0, 52.0, 80.0);

        int pocetUspechov = 0;


        for (int i = 0; i < pocetPokusov; i++)
        {
            sortedAuta.clear();
            sortedCasy.clear();

            for (int j = 0; j < 5; j++)
            {
                casy[j] = genFastCas.sample();
                auta[j] = "fast";
            }

            for (int j = 5; j < 10; j++)
            {
                casy[j] = genFuriousCas.sample();
                auta[j] = "furious";
            }


            for (int j = 0; j < 10; j++)
            {
                if (sortedCasy.size() == 0)
                {
                    sortedCasy.add(casy[j]);
                    sortedAuta.add(auta[j]);
                }
                else
                {
                    int index = 0;
                    double localCas;

                    while (true)
                    {
                        if (index == sortedCasy.size())
                        {
                            sortedCasy.add(casy[j]);
                            sortedAuta.add(index, auta[j]);
                            break;
                        }

                        localCas = sortedCasy.get(index);

                        if (casy[j] < localCas)
                        {
                            sortedCasy.add(index, casy[j]);
                            sortedAuta.add(index, auta[j]);
                            break;
                        }

                        index++;
                    }
                }
            }

            if (sortedAuta.get(0).equals("fast") && sortedAuta.get(1).equals("fast"))
            {
                pocetUspechov++;
            }
        }

        double pravdepodobnost = (double)pocetUspechov / pocetPokusov * 100;

        System.out.println("Pravedepodobnost, ze sa na pretekoch na prvych dvoch poziciach umiestnia automobili typu fast je " + pravdepodobnost);
    }

    public static void zadanie6Druhe()
    {
        final int pocetPokusov = 1000000;
        double cas;

        double[] casy = new double[10];
        String[] auta = new String[10];

        ArrayList<Double> sortedCasy = new ArrayList<>();
        ArrayList<String> sortedAuta = new ArrayList<>();

        double minimumCas;
        String minimumAuto;

        int bodyFast;
        int bodyFurious;

        TriangularRNG genFastCas = new TriangularRNG(40.0, 50.0, 75.0);
        TriangularRNG genFuriousCas = new TriangularRNG(35.0, 52.0, 80.0);

        int pocetUspechovFast = 0;
        int pocetUspechovFurious = 0;
        int remiza = 0;


        for (int i = 0; i < pocetPokusov; i++)
        {
            bodyFast = 0;
            bodyFurious = 0;

            for (int x = 0; x < 15; x++)
            {
                sortedAuta.clear();
                sortedCasy.clear();

                for (int j = 0; j < 5; j++)
                {
                    casy[j] = genFastCas.sample();
                    auta[j] = "fast";
                }

                for (int j = 5; j < 10; j++)
                {
                    casy[j] = genFuriousCas.sample();
                    auta[j] = "furious";
                }


                for (int j = 0; j < 10; j++)
                {
                    if (sortedCasy.size() == 0)
                    {
                        sortedCasy.add(casy[j]);
                        sortedAuta.add(auta[j]);
                    }
                    else
                    {
                        int index = 0;
                        double localCas;

                        while (true) {
                            if (index == sortedCasy.size())
                            {
                                sortedCasy.add(casy[j]);
                                sortedAuta.add(index, auta[j]);
                                break;
                            }

                            localCas = sortedCasy.get(index);

                            if (casy[j] < localCas)
                            {
                                sortedCasy.add(index, casy[j]);
                                sortedAuta.add(index, auta[j]);
                                break;
                            }

                            index++;
                        }
                    }
                }


                for (int j = 0; j < 10; j++)
                {
                    if (sortedAuta.get(j).equals("fast"))
                    {
                        bodyFast = bodyFast + (10 - j);
                    }
                    else
                    {
                        bodyFurious = bodyFurious + (10 - j);
                    }
                }
            }


            if (bodyFast == bodyFurious)
            {
                remiza++;
            }
            else if (bodyFast < bodyFurious)
            {
                pocetUspechovFurious++;
            }
            else
            {
                pocetUspechovFast++;
            }
        }

        System.out.println("Remiza " + remiza);
        System.out.println("Fast " + pocetUspechovFast);
        System.out.println("Furious " + pocetUspechovFurious);
    }

    public static void zadanie7()
    {
        final int pocetPokusov = 60000000;
        final double nedostatok = 150.0;
        final double prebytok = 50.0;

        double dopyt;

        int minSkladovanyPocet = 5550;
        int maxSkladovanyPocet = 5600;
        int curSkladovanyPocet;

        double naklady;
        long[] sucetNaklady = new long[(maxSkladovanyPocet - minSkladovanyPocet + 1)];
        double[] priemerneNaklady = new double[(maxSkladovanyPocet - minSkladovanyPocet + 1)];

        int najlepsieMnozstvo;
        double najlepsieNaklady;

        TriangularRNG genDopyt = new TriangularRNG(1000.0, 4000.0, 8500.0);

        for (int i = 0; i < (maxSkladovanyPocet - minSkladovanyPocet + 1); i++) {
            curSkladovanyPocet = (minSkladovanyPocet + i);

            for (int j = 0; j < pocetPokusov; j++) {
                dopyt = Math.round(genDopyt.sample());
                naklady = 0;

                if (dopyt == curSkladovanyPocet) {
                    naklady = 0;
                } else if (dopyt < curSkladovanyPocet) {
                    naklady = (curSkladovanyPocet - dopyt) * prebytok;
                } else {
                    naklady = (dopyt - curSkladovanyPocet) * nedostatok;
                }

                sucetNaklady[i] += naklady;
            }
        }

        for (int i = 0; i < (maxSkladovanyPocet - minSkladovanyPocet + 1); i++)
        {
            priemerneNaklady[i] = (double)sucetNaklady[i] / pocetPokusov;
        }

        najlepsieMnozstvo = minSkladovanyPocet;
        najlepsieNaklady = priemerneNaklady[0];

        for (int i = 0; i < (maxSkladovanyPocet - minSkladovanyPocet + 1); i++)
        {
            if (priemerneNaklady[i] < najlepsieNaklady)
            {
                najlepsieMnozstvo = (minSkladovanyPocet + i);
                najlepsieNaklady = priemerneNaklady[i];
            }
        }

        System.out.println("Najlepsie mnozstvo " + najlepsieMnozstvo + ", naklady " + najlepsieNaklady);
    }

    public static void zadanie8()
    {
        final double rozloha = 300;
        double ostava = 300;
        double kombajn;
        final int maxPocetKombajnov = 10;
        final int pocetPokusov = 1000000;
        int pocetUspechov = 0;
        final int pocetHodin = 20;

        TriangularRNG genKombajn = new TriangularRNG(1.0, 3.0, 3.5);

        for (int curPocetKombajnov = 1; curPocetKombajnov <= maxPocetKombajnov; curPocetKombajnov++)
        {
            pocetUspechov = 0;

            for (int i = 0; i < pocetPokusov; i++)
            {
                ostava = 300;

                for (int j = 0; j < pocetHodin; j++)
                {
                    for (int k = 0; k < curPocetKombajnov; k++)
                    {
                        kombajn = genKombajn.sample();

                        ostava -= kombajn;
                    }
                }

                if (ostava <= 0)
                {
                    pocetUspechov++;
                }
            }

            double pomer = ((double)pocetUspechov / pocetPokusov) * 100;
            double neuspech = 100.0 - pomer;
            System.out.println("Pri " + curPocetKombajnov + " kombajnoch je pravdepodobnost neuspechu " + neuspech);
        }
    }

    public static void zadanie9()
    {
        final int pocetPokusov = 1000000;
        final int pocetDni = 7;
        final int limit = 75;

        double[] cena = new double[pocetDni];
        double[] obsadenost = new double[pocetDni];

        cena[0] = 500.0;
        obsadenost[0] = 27.0;

        double znizenieCeny;
        double zvysenieObsadenosti;
        boolean prekrocenieLimitu = false;

        TriangularRNG genZnizenieCeny = new TriangularRNG(1.0, 4.0, 11.0);
        UniformContinuousRNG genZvysenieObsadenosti = new UniformContinuousRNG(5.0, 14.0);

        double[] sucetCien = new double[pocetDni];
        double[] priemernaCena = new double[pocetDni];

        for (int i = 0; i < pocetPokusov; i++)
        {
            cena[0] = 500.0;
            obsadenost[0] = 27.0;
            prekrocenieLimitu = false;

            for (int j = 1; j < pocetDni; j++)
            {
                if (prekrocenieLimitu)
                {
                    obsadenost[j] = obsadenost[j - 1];
                    cena[j] = cena[j - 1];
                }
                else
                {
                    znizenieCeny = genZnizenieCeny.sample();
                    zvysenieObsadenosti = genZvysenieObsadenosti.sample();

                    obsadenost[j] = obsadenost[j - 1] + zvysenieObsadenosti;

                    if (obsadenost[j] >= limit)
                    {
                        prekrocenieLimitu = true;
                        cena[j] = cena[j - 1] + (cena[j - 1] / 100 * 30);
                    }
                    else
                    {
                        cena[j] = cena[j - 1] - (cena[j - 1] / 100 * znizenieCeny);
                    }
                }
            }

            for (int j = 0; j < pocetDni; j++)
            {
                sucetCien[j] += cena[j];
            }
        }

        for (int i = 0; i < pocetDni; i++)
        {
            priemernaCena[i] = sucetCien[i] / pocetPokusov;
        }

        for (int i = 0; i < pocetDni; i++)
        {
            System.out.println("Priemerna cena v den " + i + " je " + priemernaCena[i]);
        }
    }
    
    public static void predajSpeciality() {
        double nakupnaCena;
        final double predajnaCena = 0.89;
        double vykupnaCena;
        final int nakupeneMnozstvo = 120;
        double dopyt;
        double predaneMnozstvo;

        double sucetHV = 0;
        final int pocetPokusov = 10000000;

        double zisk;
        double naklady;
        double ziskZVratenia;
        double HV;

        UniformContinuousRNG genNakupnaCena = new UniformContinuousRNG(0.40, 0.70);
        TriangularRNG genDopyt = new TriangularRNG(70.0, 90.0, 110.0);

        for (int i = 0; i < pocetPokusov; i++)
        {
            nakupnaCena = genNakupnaCena.sample();
            vykupnaCena = nakupnaCena / 2;
            dopyt = round(genDopyt.sample());

            predaneMnozstvo = min(dopyt, nakupeneMnozstvo);

            zisk = predaneMnozstvo * predajnaCena;
            naklady = nakupeneMnozstvo * nakupnaCena;
            ziskZVratenia = vykupnaCena * (nakupeneMnozstvo - predaneMnozstvo);

            HV = zisk + ziskZVratenia - naklady;

            sucetHV += HV;
        }

        double priemernyHV = sucetHV / pocetPokusov;

        System.out.println("Odhadovany priemerny HV je " + priemernyHV);
    }

    public static void predajSpecialityKonstantny()
    {
        final double nakupnaCena = 0.60;
        final double predajnaCena = 0.89;
        final double vykupnaCena = 0.30;    // vykup nepredanych kusov
        final int nakupeneMnozstvo = 100;
        final int predaneMnozstvo = 78;

        double zisk = (predajnaCena * predaneMnozstvo);
        double naklady = (nakupnaCena * nakupeneMnozstvo);
        double ziskZVratenia = (vykupnaCena * (nakupeneMnozstvo - predaneMnozstvo));

        double vysledokHospodarenia = zisk + ziskZVratenia - naklady;

        System.out.println("Vysledok hospodarennia je " + vysledokHospodarenia);
    }

    public static  void obsahObdlznika()
    {
        // velky obdlznik => x <0; 1>, y <0; 1>
        // maly obdlznik => x <0.3; 0.6>, y <0.1; 0.7>
        final int pocetPokusov = 10000000;
        int pocetUspechov = 0;
        double surX;
        double surY;

        UniformContinuousRNG generatorX = new UniformContinuousRNG();
        UniformContinuousRNG generatorY = new UniformContinuousRNG();

        for (int i = 0; i < pocetPokusov; i++)
        {
            surX = generatorX.sample();
            surY = generatorY.sample();

            if ((surX >= 0.3 && surX <= 0.6) && (surY >= 0.1 && surY <= 0.7))
            {
                pocetUspechov++;
            }
        }

        double pomer = (double)pocetUspechov / pocetPokusov;
        System.out.println("Obsah obdlznika je: " + pomer);
    }

    public static void metoda()
    {
        final int pocetPokusov = 1000000;
        final double nakupnaCena = 0.15;
        final double vykupnaCena = 0.09;
        final int pocetNakupenychBalikov = 15;


        TriangularRNG genPredajnaCena = new TriangularRNG(0.25, 0.60, 0.95);
        UniformContinuousRNG getDlzkaPrace = new UniformContinuousRNG(250.0, 420.0);    // v minutach
        ExponentialRNG getMedzeryMedziPredajmi = new ExponentialRNG(2.7);   // v minutach


        double dlzkaPrace;
        double cenaPredaj;
        double dopyt;

        int predane = 0;
        double vysledok = 0;
        double vysledokCelkovy = 0;


        double dalsiPredajVCase = 0;
        double dalsiZakaznik = 0;


        // experimenty
        for (int i = 0; i < pocetPokusov; i++)
        {
            predane = 0;
            vysledok = 0;
            dalsiPredajVCase = 0;
            cenaPredaj = genPredajnaCena.sample();
            dlzkaPrace = getDlzkaPrace.sample();
            vysledok -= (pocetNakupenychBalikov * 10) * nakupnaCena;

            predane++;
            vysledok += cenaPredaj;

            dalsiZakaznik = getMedzeryMedziPredajmi.sample();
            dalsiPredajVCase += dalsiZakaznik;

            while (dalsiPredajVCase <= dlzkaPrace)
            {
                if (predane >= (pocetNakupenychBalikov * 10))
                {
                    break;
                }

                predane++;
                vysledok += cenaPredaj;

                dalsiZakaznik = getMedzeryMedziPredajmi.sample();
                dalsiPredajVCase += dalsiZakaznik;
            }

            if (predane < (pocetNakupenychBalikov * 10))
            {
                vysledok += (((pocetNakupenychBalikov * 10) - predane) * vykupnaCena);
            }

            vysledokCelkovy += vysledok;
            System.out.println(i);
        }


        System.out.println("*****************************************************");
        System.out.println("-----------------------------------------------------");

        double priemerVysledku = vysledokCelkovy / pocetPokusov;
        System.out.println("Odhad priemerného hospodárskeho výsledku: " + priemerVysledku);

        System.out.println("-----------------------------------------------------");
        System.out.println("*****************************************************");
    }

    public static void sorting() {
        UniformDiscreteRNG generator = new UniformDiscreteRNG(0, 100);
        int random;

        final int velkostPola = 10;
        int[] pole = new int[velkostPola];

        for (int i = 0; i < velkostPola; i++) {
            random = generator.sample();

            pole[i] = random;
        }

        int najmensie = pole[0];
        int indexNajmensie = 0;
        int posun = 0;

        for (int h = 0; h < velkostPola; h++)
        {
            najmensie = pole[posun];
            indexNajmensie = posun;

            for (int i = posun; i < velkostPola; i++)
            {
                if (pole[i] < najmensie)
                {
                    najmensie = pole[i];
                    indexNajmensie = i;
                }
            }

            int temp = pole[posun];
            pole[posun] = pole[indexNajmensie];
            pole[indexNajmensie] = temp;

            posun++;
        }

        System.out.println();
    }

    public static void test()
    {
        UniformDiscreteRNG generator = new UniformDiscreteRNG(1, 100);
        int[] pole = new int[10];

        for (int i = 0; i < 10; i++)
        {
            pole[i] = generator.sample();
        }

        for (int i = 0; i < 10; i++)
        {
            System.out.println(pole[i]);
        }

        Arrays.sort(pole);
        System.out.println();

        for (int i = 0; i < 10; i++)
        {
            System.out.println(pole[i]);
        }
    }
}