package decodeTorrent;

import data.Torrent;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Decode{

    //СТОП!!! ПРОЧИТАЙТЕ ЦЕ!!!
    //Не знаю хто і чому дивиться цей коміт/код
    //але знай, я не старався в логіку, я просто
    //хотів зрозуміти як можна прочитати торент
    //файл. Мені соромно за ці if в if в if

    //Далі планується забрати ідею з перезаписом
    //масиву байтів, а просто слідити по індексу
    //на якій позиції я зараз. Також створити
    //окремі класи для читання строк, листів та
    //чисел, або просто в 1 клас засунути.


    private Map<String, String> decodeTorrent;
    private final StringBuilder decodeTorrentString = new StringBuilder();


    private byte[] torrent;
    private static int nextRead = 0;
    private static boolean closeList = false;
    private static boolean soloElement = true;



    public Torrent decode(byte[] _torrent){
        torrent = _torrent;
        decodeTorrent = new HashMap<>();

        String result = constructorInformation();

        return new Torrent();
    }


    private String constructorInformation(){
        StringBuilder result = new StringBuilder();

        while(torrent.length > 1){
            checkByte(torrent[0]);
        }

        return result.toString();
    }


    private void checkByte(byte _byte){
        soloElement = true;
        closeList = false;

        if(_byte == 'd'){
            readDictionary(1);
        }else{
            readString();
        }

    }

    private void readDictionary(int position) {
        setNextRead(position);
        decodeTorrentString.append("\n dictionary { \n");
    }


    private void readList(){
        soloElement = false;

        while(!closeList){
            readString();
        }

        decodeTorrentString.append("\n");
    }


    private byte[] readInt(int position){
        StringBuilder num = new StringBuilder();
        soloElement = false;

        while(torrent[position] != 'e'){
            num.append(new String(new byte[]{torrent[position]}));
            position++;
        }

        return num.toString().getBytes(StandardCharsets.UTF_8);
    }


    private byte[] getDiapasonByte(int posOne, int posTwo){
        byte[] result = new byte[posTwo-posOne];

        for(int i = 0; i < posTwo; i++){
            result[i] = torrent[i+posOne];
        }

        return result;
    }

    private byte[] addInfoByteArray(byte[] info, byte[] add){
        byte[] result = new byte[info.length + add.length];

        for(int i = 0; i < info.length; i++){
            result[i] = info[i];

            if(i >= info.length - 1){
                i++;
                for(int j = 0; j < add.length; j++){
                    result[i+j] = add[j];
                }
            }
        }

        return result;
    }


    //розділити на кілька методів, а то виглядає вирвеглазно. І щоб в саме в них передавалась позиція.
    private void readString(){
        byte[] result = getDiapasonByte(0, nextRead);

        if(torrent[nextRead] == 'e' && torrent[nextRead+1] == 'e'){
            if(torrent[nextRead+2] != 'd') {
                setNextRead(nextRead + 2);
                decodeTorrentString.append(new String(result, StandardCharsets.UTF_8)).append("} \n");
            }else{
                decodeTorrentString.append(new String(result, StandardCharsets.UTF_8)).append("} \n");
                readDictionary(nextRead+3);
            }

        }else if(torrent[nextRead] == 'i'){
            byte[] num = readInt(nextRead+1);
            byte[] resultNum = addInfoByteArray(result, num);
            setNextRead(nextRead + num.length + 2);
            decodeTorrentString.append(new String(resultNum, StandardCharsets.UTF_8)).append("\n");

        }else if(torrent[nextRead] == 'l'){
            decodeTorrentString.append(new String(result, StandardCharsets.UTF_8));
            if(torrent[nextRead + 1] == 'l') {
                setNextRead(nextRead + 2);
                readList();
            }else{
                if(torrent[nextRead+1] == 48 || torrent[nextRead+1] == 49 || torrent[nextRead+1] == 50 || torrent[nextRead+1] == 51 || torrent[nextRead+1] == 52 || torrent[nextRead+1] == 53 || torrent[nextRead+1] == 54 || torrent[nextRead+1] == 55 || torrent[nextRead+1] == 56 || torrent[nextRead+1] == 57 || torrent[nextRead+1] == 58){
                    setNextRead(nextRead+1);
                }else {
                    deleteByteInTorrent(nextRead + 1);
                    nextRead = 0;
                }
                readList();
            }

        }else if(torrent[nextRead] == 'd'){
            readDictionary(nextRead+1);
            decodeTorrentString.append(new String(result, StandardCharsets.UTF_8)).append("\n");
        }else {
            if(torrent[nextRead] == 48){
                setNextRead(nextRead+2);
                decodeTorrentString.append(new String(result, StandardCharsets.UTF_8)).append("\n");
                soloElement = false;
            }else {
                setNextRead(nextRead);
                decodeTorrentString.append(new String(result, StandardCharsets.UTF_8));
            }

            if(soloElement && !new String(result, StandardCharsets.UTF_8).equals("pieces ")){
                soloRead();
            }else if(new String(result, StandardCharsets.UTF_8).equals("pieces ")){
                byte[] a = new byte[nextRead];
                for(int i = 0; i < nextRead; i++){
                    a[i] = torrent[i];
                }
                String info = new String(a, StandardCharsets.UTF_8);
                decodeTorrentString.append("{").append(info).append("}").append("\n");
                setNextRead(a.length);
            }
        }

    }


    //https://stackoverflow.com/questions/11208479/how-do-i-initialize-a-byte-array-in-java
    private void soloRead(){
        byte[] result = getDiapasonByte(0, nextRead);
        if(torrent[nextRead] == 'e' && torrent.length - nextRead == 1){
            deleteByteInTorrent(nextRead);
            nextRead = 0;
        }else {
            setNextRead(nextRead);
        }
        decodeTorrentString.append(new String(result, StandardCharsets.UTF_8) + "\n");
    }


    private void setNextRead(int position){
        StringBuilder num = new StringBuilder();
        boolean exit = false;

        if (torrent[position] == 'e' && torrent[position + 1] == 'e') {
            closeList = true;
            position += 3;
        }else if(torrent[position-1] == 'e' && torrent[position-2] == 'e'){
            closeList = true;
        }else if(torrent[position-1] == 'e' && torrent[position] == 'e'){
            closeList = true;
            position++;
        }

        for (int i = position; i < torrent.length; i++) {
            switch (torrent[i]) {
                case 48: //0  String 0 ASCII 48
                case 49: //1  String 1 ASCII 49
                case 50: //2  ...
                case 51: //3
                case 52: //4
                case 53: //5
                case 54: //6
                case 55: //7
                case 56: //8
                case 57:
                    num.append(new String(new byte[]{torrent[i]}, StandardCharsets.UTF_8)); //9
                    break;
                default:
                    nextRead = Integer.parseInt(String.valueOf(num));
                    deleteByteInTorrent(i + 1);
                    exit = true;
                    break;
                }

                if (exit) {
                    break;
                }
        }


    }


    private void deleteByteInTorrent(int position){
        byte[] newTorrent = new byte[torrent.length - position];
        for(int i = 0; i < torrent.length - position; i++){
            if(torrent.length >= i+position) {
                newTorrent[i] = torrent[i + position];
            }else{
                newTorrent[i] = 0;
            }
        }
        torrent = newTorrent;
    }


//Потрібно створити строку яка буде зберігати в собі всю інфу
//d - початок словника, він містить якісь файли. Позначатиму {}.
//l - означає, що після цього символу йде список.
//i - далі йде число.
//e - закінчення d, l або i.

//Приклад: d8:announce22:udp://opentor.org:271013
//у нас є d і це означає, що ми відкриваємо {} (тільки відкриваємо, e закриє їх) -
    /*
    {
    Тепер переходимо до наступного індексу 1, бачимо число 8 воно означає,
    що наспний ключ матиме 8 символів "announce", після якого йдуть цифри,
    вони говорять скільки "announce" буде містити символів, це вийде -
    "udp://opentor.org:2710", можна помітити, що нема "13", це все через те,
     що число 13 вказує скільки символів матиме наспний ключ. Поки що у нас є:

    {
        announce : announce22:udp://opentor.org:2710

    Наступним йде ":announce-listll22:", можна порахувати і запевнетись що
    він має 13 символів, далі йде ll22, що воно означає? l - це позначка списку,
    отже відкриваємо [] після цього йде також l так буває, знову відкриваємо [
    потрібно не забувати про перенесення /n, щоб у нас це все в одному рядку не
    записувалось. Далі вже йде зрозуміле значення 22 яке означає скільки матиме
    символів перший елементи в списку, вийде:

    {
        announce : announce22:udp://opentor.org:2710
        announce-list : udp://opentor.org:2710$

    Також в списках в кінці кожного нового елемента ставимо якийсь знак який буде
    надалі дозволити розділити строку, зазвичай це &, але я використаю $.
    перший елемент мав закінчення el31, e - закінчення посилання, далі l яке означає
    що список продовжується. 31 - кількість символів наступного посилання. Але
    потрібно розуміти одне не завжди є "e" та "l" замість них можуть бути інші
    символи або цифри, тому потрібно просто робити розрахунок на те, що в кінці
    є 2 символи які ми й так ніяк не конвертуємо, і ще 2 числа які дають інформацію
    про довжину наступні строки, більше нам нічого і не потрібно знати.

    і тепер повторюємо цю операцію поки не дойдемо до 2 ee.
        {
        announce : announce22:udp://opentor.org:2710

        announce-list : udp://opentor.org:2710$http://retracker.local/announce
        $http://retracker.local/announce$http://tracker.filetracker.pl:8089/announce
        $http://tracker2.wasabii.com.tw:6969/announce$http://tracker.grepler.com:6969/announce
        $http://125.227.35.196:6969/announce$http://tracker.tiny-vps.com:6969/announce
        $http://87.248.186.252:8080/announce$http://210.244.71.25:6969/announce
        $http://46.4.109.148:6969/announce$udp://46.148.18.250:2710
        $http://tracker.dler.org:6969/announce$udp://[2001:67c:28f8:92::1111:1]:2710

    наступний елемент: udp://ipv6.leechers-paradise.org:6969ee18
    тут в кінці 2 ee які кажуть, що список закінчився. Та інформація про наступний
    елемент який має 18 символів. :azureus_propertiesd17:dht_backup_enablei0ee10
    Читаємо 18 символів і отримуємо "azureus_propertiesd" далі йде d яке каже
    відкривати {, робимо це:

        {
        announce : announce22:udp://opentor.org:2710

        announce-list : udp://opentor.org:2710$http://retracker.local/announce .......

        azureus_properties{
            dht_backup_enable : 0
        }

   також можна помітити, що в кінці enable присутня англійська "i" яка каже нам
   що наступні символи - число, у нашому випадку це 0. бачимо ее і закриваємо
   дужки. і так далі.....

:azureus_propertiesd17:dht_backup_enablei0ee10
:created by18:qBittorrent v4.2.513
:creation datei1603019611e4
:infod5

:filesld6

:lengthi142971437056e4
:pathl10:cpy-ma.isoeed6

:lengthi11072e4
:pathl10:cpy-ma.nfoeee4

:name20:Marvels.Avengers-CPY12
:piece lengthi16777216e6

     */


}
