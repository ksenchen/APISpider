package model;

public class LocalHtmlInfo {
	private String localFilePath;
	private String url;

	public LocalHtmlInfo() {
		super();
	}

	public LocalHtmlInfo(String localFilePath, String url) {
		super();
		this.localFilePath = localFilePath;
		this.url = url;
	}

	public String getLocalFilePath() {
		return localFilePath;
	}

	public void setLocalFilePath(String localFilePath) {
		this.localFilePath = localFilePath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localFilePath == null) ? 0 : localFilePath.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocalHtmlInfo other = (LocalHtmlInfo) obj;
		if (localFilePath == null) {
			if (other.localFilePath != null)
				return false;
		} else if (!localFilePath.equals(other.localFilePath))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
