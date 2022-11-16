package decodeTorrent.convert.data;

import java.util.Date;
import java.util.List;

public class Torrent {

    private String torrentStingFormat;
    private String announce;
    private List<String> announceLit;
    private Date creationDate;
    private String comment;
    private String createdBy;
    private String encoding;
    private List<TorrentElements> filesElements;
    private Long size;
    private String infoHash;
    private String name;


    public String getTorrentStingFormat() {
        return torrentStingFormat;
    }

    public void setTorrentStingFormat(String torrentStingFormat) {
        torrentStingFormat = torrentStingFormat;
    }

    public List<String> getAnnounceLit() {
        return announceLit;
    }

    public void setAnnounceLit(List<String> announceLit) {
        this.announceLit = announceLit;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getInfoHash() {
        return infoHash;
    }

    public void setInfoHash(String infoHash) {
        this.infoHash = infoHash;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TorrentElements> getFilesElements() {
        return filesElements;
    }

    public void setFilesElements(List<TorrentElements> filesElements) {
        this.filesElements = filesElements;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }


}