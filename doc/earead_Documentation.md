EA Reader Dokumentation
=======================

### Seerhein-Lab
### Mai 2013 � Juli 2013
### Norbert Kunstek
### Tobias Keh

Einf�hrung
----------
Das Ziel des Projekts ist es, ein Datenbankschema in einem UML Werkzeu
zu modellieren und daraus eine Schemadefinition f�r einen Java OR-Mapper
zu erzeugen. Daf�r soll als UML-Werkzeug Sparx Enterprise Architect
und als OR Mapper Torque verwendet werden.
Da das manuelle Erstellen des Schemas mit viel Arbeitsaufwand verbunden ist, 
kann hierbei durch Einbindung in den Workflow die Automatisierung 
der Arbeitsabl�ufe deutlich erh�ht werden. 
F�r kommende Versionen ist hierf�r ein entsprechendes Maven Plug-In angedacht.
 Damit w�re dann die Ausf�hrung durch einen Build-Server m�glich.
 Ein weiterer Vorteil dieses Vorgehens besteht darin,
 dass das Klassen-Diagramm somit gleichwertig zum Schema ist.
 �nderungen werden immer direkt am Klassen-Diagramm vorgenommen
und dieses ist somit immer auf dem aktuellsten Stand. 
Bei manueller Erstellung des Schemas hat das Klassen-Diagramm lediglich eine 
dokumentierende Funktion und die Wahrscheinlichkeit ist sehr hoch,
dass auf Grund des gr��eren Aufwands die Versionen des Klassen-Diagramms
und des Schemas divergieren.

L�sungsans�tze
--------------
Gesucht ist eine M�glichkeit, das vom Enterprise Architect exportierte
UML Klassen-Diagramm im EA Reader zu importieren.
Hierzu gibt es eine Vielzahl an M�glichkeiten.
Es gibt schon vorhandene Projekte, mit denen zus�tzlich noch Programmcode
generiert werden kann, wie zum Beispiel KISSMDA oder AndroMDA. 
Da dies f�r das Projekt aber nicht ben�tigt wird,
haben wir folgende f�nf M�glichkeiten betrachtet

1. Export im Ecore Format und Import mit Eclipse EMF
2. Export im XMI Format und Import mit Eclipse EMF
3. Export im XMI Format und Import mit Eclipse UML2
4. Export im XMI Format und manuelles Parsen des Exports
5. Direktes Einlesen der Enterprise Architect Datei �ber die Sparx Java API

### 1. Ecore

_Pro_

Die Erstellung des Exports in das Ecore Format ist sehr einfach m�glich. 
Die n�tigen Einstellungen k�nnen im EA vorgenommen werden. 
Das Auslesen in Java ist ebenfalls sehr einfach m�glich
und die Informationen sind einfach zug�nglich. 
Des Weiteren wird das Ecore Format vom Eclipse Modeling Framework (EMF)
zur Persistierung benutzt. 
Daher w�re diese M�glichkeit des Exports interessant,
sofern man mit EMF an dem Export weiter arbeiten m�chte. 

_Contra_

Der Enterprise Architect exportiert nur einen Bruchteil der im Diagramm 
enthaltenen Informationen in die Ecore Export Datei. 
Daher ist diese L�sung nicht zu gebrauchen.

### 2. EMF

_Pro_

Die Erstellung des Exports in das XMI Format ist im Enterprise Architect
sehr einfach m�glich.
Die Informationen sind �ber Java und das EMF Eclipse Modeling Framework
relativ einfach zug�nglich.

_Contra_

Allerdings ist das EMF zur Modellierung gedacht, 
weshalb es die von uns ben�tigte Funktionalit�t bei weitem �bersteigt.
 Prinzipiell h�tten wir diese M�glichkeit w�hlen k�nnen,
 allerdings hat sich mit UML2 noch eine bessere M�glichkeit ergeben.

### 3. UML2

_Pro_

Die Erstellung des Exports in das XMI Format ist im Enterprise Architect 
sehr einfach m�glich.
Die Informationen sind in Java �ber die UML2 Bibliothek sehr einfach
zug�nglich.
Eclipse UML2 ist eine UML Implementierung von Eclipse basierend auf EMF. 
Da es sich beim Import um UML Klassendiagramme handelt,
bietet sich dieser L�sungsansatz an.
Beim Import wird ein entsprechender Objektgraph erstellt,
mit dem dann gearbeitet werden kann. 
Beinahe alle ben�tigten Informationen werden beim Import �bernommen
und stehen beim Mapping auf das Torque Schema zur Verf�gung.

_Contra_

Vereinzelte Informationen wie maximale L�nge eines Attributs
oder Information dar�ber, ob ein Attribut Prim�rschl�ssel ist oder nicht,
m�ssen im Vorfeld des XMI Imports mittels einer Transformation
�ber entsprechende XMI IDs zugeordnet werden. 
Au�erdem m�ssen alle zus�tzlich vom Enterprise Architect
angebotenen Informationen deaktiviert werden, 
was allerdings einfach m�glich ist.

### 4. Manuell parsen

_Pro_

Die Erstellung des Exports in das XMI Format ist im Enterprise Architect
sehr einfach m�glich.
Da man den Parser selbst programmiert, hat man die volle Kontrolle
�ber die Informationen, welche aus dem Enterprise Architect Export
in das Programm importiert werden. 
Dadurch sind die Informationen relativ leicht zug�nglich. 

_Contra_

Allerdings ist der Entwicklungs-/Wartungs- und Weiterentwicklungsaufwand
im Vergleich zu den anderen L�sungen viel zu hoch.
Da der Enterprise Architect kein reines standard-XMI exportiert,
liegt die Vermutung nahe, dass sich der Export folgender Versione
mehr oder weniger vom aktuellen Export Format unterscheidet 
und somit eine Verwendung einer dieser Versionen
einen entsprechend hohen Arbeitsaufwand nach sich zieht.

### 5. Sparx Java API

_Pro_

Die Verwendung der von Sparx angebotenen Java API ist eigentlich die eleganteste.
Da man direkt auf die Enterprise Architect Datei zugreift,
ist ein extra Export und Import im Programm �berfl�ssig.
Des Weiteren hat man keinen Aufwand bei der Wartung, 
da dies (vermutlich) von Sparx durchgef�hrt wird. 

_Contra_

Was sich als un�berwindbare H�rde herausgestellt hat ist die Tatsache,
dass die Sparx Java API auf die Verwendung einer DLL
und der Java Version 5, 32 bit angewiesen ist. 
Dadurch ist die Plattformunabh�ngigkeit von Java Anwendungen hinf�llig.

### Entscheidung

Die Entscheidung ist auf die L�sung mit dem Export des Diagramms im XMI Format
und dem Import ins Java Programm �ber die Eclipse UML2 Bibliothek gefallen.
Hierbei ist der Entwicklungs- und Wartungsaufwand
und die manuelle Bearbeitung des Exports vertretbar
und die Plattformunabh�ngigkeit wird gewahrt.

Arbeitsablauf
-------------

### Ben�tigte Programme und Versionen

#### Mindestanforderungen

* Java J2SE-1.5, 32 oder 64 bit
* Enterprise Architect Version 8 oder h�her

#### Vorgehen im EA

Folgende Vorgehensweise beim Arbeiten mit dem Enterprise Architect
ist sehr wichtig da es ansonsten zu Problemen bei der Verwendung des EA Readers
kommen kann.

Schritte zum Export des UML Klassendiagramms aus dem Enterprise Architect:

1.) Rechtsklick auf Domain Model -> Import/Export

![Domain Model](images/DomainModel.png "Domain Model")

![Export Menu](images/ExportMenu.png "Export Menu")

2.) Publish

![Export Package to XMI](images/ExportPackageToXMIDialog.png "Export Package to XMI")

3.) XMI Version 2.1 w�hlen

![Publish Model Package Settings](images/publishModelPackage.png "Publish Model Package Settings")

4.) EA Graphiken exportieren -> uncheck

![Publish Model Package Settings](images/publishModelPackage_2.png "Publish Model Package Settings")

5.) Exclude EA Extensions -> check

![Publish Model Package Settings](images/publishModelPackage_3.png "Publish Model Package Settings")

6.) Klick Export

![Publish Model Package Settings](images/publishModelPackage_4.png "Publish Model Package Settings")

#### Probleme

Wenn die beiden Optionen Export Diagrams und Exclude EA Extensions 
nicht unchecked beziehungsweise checked werden, 
kommt es beim Einlesen �ber die Eclipse UML2 Bibliothek zu Fehlern, 
da die Bibliothek den Input nicht interpretieren kann 
und den Objektgraphen nicht erstellt.

Funktion Reader
---------------

### Transformator

#### Anpassung der Datentypen

Da der Enterprise Architect keine Java konformen Datentypen ausgibt,
erg�nzt der Transformator in einem ersten Schritt die XMI Export Datei
mit einem Abschnitt, in dem den Enterprise Architect Datentypen
entsprechende Java Datentypen zugeordnet werden:

    <xmi:Extension extender="Enterprise Architect" extenderID="6.5">
      <primitivetypes>
        <packagedElement name="EA_PrimitiveTypes_Package"
          visibility="public" xmi:id="EAPrimitiveTypesPackage"
          xmi:type="uml:Package">
            <packagedElement name="EA_Java_Types_Package"
              visibility="public" xmi:id="EAJavaTypesPackage"
              xmi:type="uml:Package">
                <packagedElement name="int" visibility="public"
                  xmi:id="EAJava_int" xmi:type="uml:PrimitiveType"/>
                <packagedElement name="String" visibility="public"
               xmi:id="EAJava_String" xmi:type="uml:PrimitiveType"/>
            </packagedElement>
        </packagedElement>
      </primitivetypes>
    </xmi:Extension></xmi:XMI>

#### Erg�nzung der Schl�sselattribute

Die Schl�sselattribute sind in der XMI Export Datei �ber XMI IDs
als Custom Profile eingetragen und nicht als Attributeigenschaft isID="true".
Daher erg�nzt der Transformator diese Attributeigenschaft
bei den Schl�sselattributen.

    <packagedElement name="Class1" visibility="public"
      xmi:id="EAID_9F0EBB09_7974_41f4_B1E9_D9EBB2099E6D"
      xmi:type="uml:Class">
        <ownedAttribute isDerived="false" isDerivedUnion="false"
          isID="true" isOrdered="false" isReadOnly="false" isStatic="false"
          isUnique="true" name="class1_ID" visibility="private"
          xmi:id="EAID_85F5481E_795C_4c93_A204_F71B3AC0CFBA"
          xmi:type="uml:Property">`
      
#### Erg�nzung der L�ngenangaben der Attribute

Die Angabe der maximalen L�nge der einzelnen Attribute ist im Enterprise Architect
nicht von Haus aus m�glich, daher werden sie als Custom Profile abgespeichert und exportiert.
Da die UML2 Bibliothek diese Werte so nicht einlesen kann, sucht der Transformator
analog zu der Zuordnung der Schl�sselattribute die entsprechenden Werte 
und ordnet sie den Attributen unter ??? zu. 
(Muss noch erg�nzt werden, fehlt noch im EA Reader)

#### Einlesen als UML2 Repository

Die XMI Export Datei wird nach der Bearbeitung durch den Transformator
mit Hilfe der Eclipse UML2 Bibliothek eingelesen. 
Dabei wird ein sogenanntes UML2 Repository erstellt, 
in das das UML Klassendiagramm eingelesen und von Java Seite aus zugreifbar macht.
Dieser Objektgraph beinhaltet Modelle, die wiederum aus Klassen,
Assoziationen und Vererbungen bestehen. 
Diese beinhalten dann wiederum spezifische Attributwerte.
(Angabe des Speicherorts der EA Export Datei fehlt noch)

#### Auslesen und Mapping auf Torque Schema

Dieser Objektgraph wird mehrfach durchlaufen, 
um die vorhandenen Informationen �ber Klassen, Assoziationen und Vererbungen
entsprechend auf das Torque Schema abzubilden.
Aktuell ist es m�glich folgenden Assoziationen abzubilden:
0..1 - 1, 0..* - 1, 1 - 1, 1 - 1..*, 1 - , 1..* - 1, * - 1
Hierbei werden f�r die Klassen entsprechend Tabellen angelegt.
Die Attribute der Klassen werden dann auf Spalten der Tabellen abgebildet
und die f�r SQL interessanten Eigenschaften und Werte werden �bernommen.
Fremdschl�ssel der Assoziationen werden automatisch eingef�gt
und entsprechende Spalten in den Tabellen hinzugef�gt. 
Vererbung ist bei Torque nur als Single-Table-Inheritance m�glich.
Das hei�t, dass alle Attribute sowohl der erbenden als auch der vererbenden Klasse
in eine gemeinsame Tabelle geschrieben werden. 
Hierzu wird eine inheritance Spalte in der erbenden Tabelle angelegt.
m - n Assoziationen k�nnen aktuell noch nicht abgebildet werden.

#### Torque Schema Ausgabe

Im letzten Schritt wird dann das Torque Schema als XML Datei ausgegeben.
Es beinhaltet alle n�tigen Informationen. Dies geschieht mit Hilfe von JAXB.


