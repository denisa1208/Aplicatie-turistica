# Proiect POO 2025

In cadrul acestui proiect am realizat dezvoltarea unei aplicatii turistice care sa permita
implementarea mai multor clase, implementarea unei baze de date, folosindu-ma de 4 design pattern-uri.


### Clasa `Database`

Pentru a implementa aceasta clasa m-am folosit de desing pattern-ul `Singleton`. Astfel m-am asigurat
ca exista o singura instanta a bazei de date, iar urmatoarele motive pentru care am ales sa folisesc
acest desigm pattern sunt:

- am evitat crearea de instante multiple

Noi a trebuit sa implementam o singura baza de date, si astfel s-a potrivit folosirea desing-ului
Singleton. Singleton garanteaza ca se lucreaza cu o singura baza de date de-a lungul programului.


- accesul rapid de date

Prin metoda `getInstance()` puteam sa accesam elementele din baza de date fara sa cream o noua 
instanta. De exemplu: `Database database = Database.getInstance();`

Totodata, daca voiam de exemplu sa accesam muzeele din aceasta baza de date, puteam sa ma folosesc
direct de comanda: `Set<Museum> museums = database.getMuseums();`


- control asupra datelor

Puteam sa resetam, sa modificam baza de date fara a fi afectate restul metodelor implementate in proiect.


### Clasa `Museum`

Pentru aceasta clasa folosit desing pattern-ul `Builder`, deoarece aveam unele campuri optionale,
deoarece nu toate muzeeele aveau aceleasi campuri de definitie, unele aveau mai putine campuri
sau altele diferite.

Astfel, de fiecare data cand cream un muzeu, ii atribuiam campurile obligatorii prin intermediul
constructorului de baza, apoi ii asociam si restul atributelor optionale. De exemplu:

````java
 museum = new Museum.MuseumBuilder(name, code, cod_institution, location)
                            .setFoundingYear(foundingYear_parse)
                            .setCategory(category)
                            .setFax(fax)
                            .setEmail(email)
                            .setManager(manager)
                            .setProfile(principle_profile)
                            .setUrl(url)
                            .setPhoneNumber(phone)
                            .setError(0).build();
````

De asemenea, codul devine mai usor de inteles si de scris, si mai organizat.

Am folosit in interiorul clasei variabile declarate `final`, deoarece acestea nu mai pot fi modificate
odata ce muzeuln a fost creat.

Am ales in special sa folosesc `Builder Pattern` pentru modularitate, dar si ca sa fie codul mai clar
cand am mai multe campuri optionale.


### Clasele `Person`, `Person Factory` 

Fiind o clasa generica de la care pot extinde clasele copil `Student` si `Profesor`, am ales sa 
folosesc `Factory Pattern`.

Urmatoarele motive pentru care am ales acest design pattern sunt:

- crearea instantelor `Student` si `Profesor` in functie de rolul primit

- am evitat folosirea de cod duplicat prin intermediul metodei `createPerson()`, care decide daca
trebuie sa creeze un Student sau un Profesor

- am ascuns detaliile de implementare a claselor, deoarece `createPerson()` nu trebuie sa stie ce
constructori au clasele `Student`, `Profesor`

- putem sa cream mai usor alte tipuri de persoane modificand doar metoda `createPerson()`

In concluzie, acest pattern m-a ajutat sa extind o peroana in functie de rolul ei, fara a duplica
bucati de cod, pentru scalabilitate, dar si pentru o posibila extindere mai usoara in viitor.


### Clasa `Professor`

Am implementat clasa `Professor` folosindu-ma de `Observer pattern`. Observer pattern este utilizat
atunci cand un subiect (muzeele) trebuie sa notifice mai multe obiecte (Profesorii) asupra unor
schimbari aduse in program. Am folosit acest pattern pentru a implementa evenimentele de tip
`Listener`, pentru ultimul test. 

Urmatoarele motive pentru care am folosit Observer Pattern:

- independenta intre obiecte

Muzeele au rolul de a aduce update-uri catre profesori. Astfel, chiar daca avem clase total
independente, acestea pot sa transmita/primeasca notificari cu privire la ce se intampla in
cadrul programului.

- notificari automate catre Observers

Daca muzeul trimite un mesaj despre despre o expozitie noua, toti profesorii vor fi anuntati 
automat.


- Fiecare clasa are o anumita responsabilitate

Muzeele doar notifica profesorii, iar acestia doar primesc notificarile

In concluzie, am folosit Observer Pattern pentru scalabilitate, modularitate, pentru a crea 
clase independente.


## Comenzile implementate

### Comanda `ADD MUSEUM`

Dupa ce citeam datele din fisiere corespunzatoare campurilor pentru clasa `Museum`, cream
managerul, locatia, si muzeul, apoi cream si grupul corespunzator id-ului muzeului. 
In final adaugam in baza de date muzeul si grupul creat.

In cazul in care intampinam o eroare la adaugarea sau crearea muzeului, am tratat exceptia in 
main, si afisam urmatorul mesaj `Exception: Data is broken. ## (<line>)`


### Comanda `ADD GUIDE`

Am verificat daca grupul exista mai intai, apoi in cazul in care nu exista, il cream in interiorul
metodei `addGuide`. 

Am tratat exceptiile in care ghidul exista deja in muzeu la acea perioada sau daca ghidul nu este profesor
apoi abia la final, il adaug daca trece de toate aceste exceptii.

- tratarea exceptiilor

````java
if (ok_guide == 1) {
        String output = museum_code + " ## " + timetable + " ## GuideExistsException: Guide already exists. ## (" + "new guide: " + "surname=" + guide_current.getSurname() + ", " + "name=" + guide_current.getName() + ", role=" + role + ", age=" + guide_current.getAge() + ", email=" + guide_current.getEmail() + ", school=" + guide_current.getSchool() + ", " + name_ocupation +"=" + guide_current.getExperience() + ")";
        writeToFile(namefile, output);
        throw new GuideExistsException("## GuideExistsException: Guide already exists. ##");

}


name_ocupation = "studyYear";
String output = museum_code + " ## " + timetable + " ## GuideTypeException: Guide must be a professor. ## (" + "new guide: " + "surname=" + surname + ", " + "name=" + name + ", role=" + role + ", age=" + age + ", email=" + email + ", school=" + school + ", " + name_ocupation +"=" + year_study_or_experience + ")";
writeToFile(namefile, output);
            throw new GuideTypeException("## GuideExistsException: GuideTypeException: Guide must be a professor. ##");
````

### Comanda `FIND GUIDE`

Dupa ce gaseam grupul corespunzator id-ului muzeului, cautam ghidul in lista de ghizi.
Daca nu-l gaseam, tratam exceptiile, iar abia la final il cautam in lista de ghizi si
afisam mesajul corespunzator `<museumCode> ## <timetable> ## guide found: <person>`


### Comanda `REMOVE GUIDE`

Verificam mai intai ca exista grupul, apoi dupa ce tratam exceptiile daca era cazul, cautam
ghidul si il inlocuiam cu un ghid care are atributele nule, si apelam metoda 
`museum.removeObserver((Professor) remove_person);`


### Comanda `ADD MEMBER`

Verificam daca grupul exista mai intai, apoi daca in grupul respectiv erau mai mult de 10 membrim,
tratam exceptia `o <museumCode> ## <timetable> ## GroupThresholdException: Group cannot have more than 10 members. ## (new member:<person>)`


In final, adaugam membrul in baza de date si in grupul corespunzator codului muzeului.
(`group_found.getMembers().add(person);`)

### Comanda `FIND MEMBER`

Am verificat mai intai daca exista grupul, am tratat exceptia pentru grup negasit, apoi
am cautat membrul in lista de membri si afisam informatiile in fisier daca-l gaseam, altfel
tratamn exceptia `- <museumCode> ## <timetable> member not exists: <person>`


### Comanda `REMOVE MEMBER`

Am verificat mai intai daca exista grupul asociat cu id-ul muzeului, apoi am verificat daca 
exista persoana respectiva in grup. Daca nu exista, aruncam exceptia `<museumCode> ## <timetable> ## PersonNotExistsException: Person was not found in the group. ## (<person>)`
In final, odata gasit, il eliminam din lista de membri `group_found.getMembers().remove(remove_person);`


### Comanda `ADD EVENT`

In cadrul acestei comenzi apelam metoda `museum.notifyObservers(message, writer);` dupa ce gaseam grupul.
Aceasta metoda instiinta profesorii ca a fost adaugat un eveniment si afisa mesajul `To: <emailAddress> ## Message: <museumName> <museumCode>) <organizerMessage>`
in fisierul de iesire.
