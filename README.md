Team 1 - Videos DB.

In cadrul acestei teme am implementat o platformă (Netflix wannabe) simplificată
    ce ofera informații despre filme și despre seriale. Utilizatorii pot să primească recomandări personalizate pe baza preferințelor.

Pe baza scheletului am implementat functionalitatea pentru diferitele actiuni pe care
    le poate da un user.

Am folosit o clasa diferita pentru implementarea fiecarei actiuni, precum si clase
    utilitare pentru a modulariza cat mai mult codul si a nu scrie cod duplicat.


Am creeat clase pentru fiecare tip de comanda, query sau recomandare ce o poate face
    un user. Fiecare clasa contine metoda care realizeaza actiunea ceruta, precum si metode ajutatoare care sunt accesibile doar in clasa respectiva.

Clasele utilitare (Helper, Filters, Sort) creeate de mine contin metode ajutatoare ce
    ajuta la modularizarea codului (ex: Sortarea unui hashmap, gasirea unor filtre din actiune, scriere in output).

In clasa Main, se va itera prin fiecare actiune si in functie de tipul acesteia,
    se va creea clasa si se va apela metoda necesara pentru o comanda, un query sau o recomandare.

Clasele command, query si recommendation vor executa  comanda, query-ul sau recomandarea in functie de tipul acesteia.

Fiecare clasa creeata contine javadoc si cateva detalii despre implementarea in sine. 




