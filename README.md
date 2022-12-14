## TorrentDecoder is a library that will allow you to decode a torrent file.

----

## Download steps (for intellij idea or android studio):

<!-- DOWNLOAD BUTTON -->
1. Download the release you need. You can download the last version of the library by clicking on this button: 
[download](https://github.com/Hlushcko/TorrentDecoder/releases/download/%230001/TorrentDecoder-0.2.jar)
<!-- EDN DOWNLOAD BUTTON -->

2. open "Project Structure". (File/Project Structure...):
<img src="https://user-images.githubusercontent.com/72913351/205656788-e5e1f5b7-dbc6-455d-b7c7-4807488bd9da.png" width="300">

3. Open libraries and click on plus
<img src="https://user-images.githubusercontent.com/72913351/205656795-8eb20059-a018-40d7-acea-21438f388c2b.png" width="500">

4. In the window that opens, click on "java"
<img src="https://user-images.githubusercontent.com/72913351/205656797-060736ef-4eae-4a1b-87ef-2e9ceec826f3.png" width="300">

5. Looking for the release you downloaded and click "OK"
<img src="https://user-images.githubusercontent.com/72913351/205656798-0c56fc45-07f2-4121-a002-725b0b1cbf30.png" width="500">

6. Choose a module in which this library will be added
<img src="https://user-images.githubusercontent.com/72913351/205656801-fd551ecd-9022-4fc8-b023-7c328ebb0135.png" width="400">

### Wonderfully! Now you can use it

----

## How to work with TorrentDecoder

#### All you need to decode a torrent file is: 
* 1. Torrent class. 
* 2. TorrentDecoder class 
* 3. Link to a torrent file or an already read torrent file in inputStream or byte[]

#### The decodeTorrent method from the TorrentDecoder class can take: InputStream, byte[] or File and returns to you the already decoded torrent file. It may contain:

* announce.
* announce list.
* creation date.
* comment.
* created by.
* encoding.
* files element.
* pieces length.
* torrent hash.
* name.
* pieces.
* private.
* publisher.
* publisher url.

### You should understand that not all torrent files may contain coding, comment, or any other elements. You will usually get the following items:

* announce.
* announce list.
* creation date.
* created by.
* files element.
* pieces length.
* torrent hash.
* name.
* pieces.


#### You should also place the decoding process in Try catch. It is also recommended to perform this task in another stream, since decrypting large files (5 megabytes) can take a few seconds and block the user interface.


```
try{
  Torrent torrent = new TorrentDecoder().decodeTorrent( "your File, InputStream or byte[]"  );
}catch(Exception e){
  e.printStackTrace();
}
```

### Example of torrent decoding using the File class


```
private final string PATH_TORRENT_FILE = "your path";

try{
  Torrent torrent = new TorrentDecoder().decodeTorrent(new File(PATH_TORRENT_FILE));
}catch(Exception e){
  e.printStackTrace();
}
```

