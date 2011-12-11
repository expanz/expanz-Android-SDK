package com.expanz.app.data;

import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * A basic local memory cache for image data to get around some of the
 * performance issues with ListView and the retrieval of image data from a
 * network.
 * 
 * TODO probably need to overflow to a local disk cache
 * 
 * This is a somewhat temporary solution until an end to solution involving the
 * server side has been established, e.g. pagination using cursors etc
 * 
 */
public class ImageCache {
	
	/**
	 * Holds the cached image data
	 */
	private static Map cache;
	
	/**
	 * Singleton instance of this class
	 */
	private static ImageCache instance;
	
	/**
	 * Private Ctor.
	 */
	private ImageCache() {
	
		cache = Collections.synchronizedMap(new ImageMap(50)); 
	}
	
	/**
	 * Returns the singleton instance
	 * 
	 * @return the instance
	 */
	public static ImageCache getInstance() {
		
		if(instance == null) {
			instance = new ImageCache();
		}
		
		return instance;
	}
	
	/**
	 * Returns the bitmap associated with the key. 
	 * Assume the object will not be transiently referenced, hence doesn't return a soft/weak ref
	 * 
	 * @param key
	 * @return
	 */
	public Bitmap get(Id key) {
		
		SoftReference<Bitmap> ref = (SoftReference<Bitmap>) cache.get(key);
		
		if(ref != null) {
			return ref.get();
		} else {
			return null;
		}
		
	}
	
	/**
	 * 
	 * @param key
	 * @param bitmap
	 */
	public void add(ImageCache.Id key, Bitmap bitmap) {
		
		cache.put(key, new SoftReference<Bitmap>(bitmap));
		
	}
	
	/**
	 * Clear the underlying data structure 
	 * 
	 * @param key
	 */
	public void clear() {
		cache.clear();
	}
	
	/**
	 * Data Structure for the actual cache. 
	 *
	 */
	private class ImageMap extends LinkedHashMap<Id, SoftReference<Bitmap>> {
		private final int maxEntries;

		public ImageMap(final int maxEntries) {
			super(maxEntries + 1, 1.0f, true);
			this.maxEntries = maxEntries;
		}

		@Override
		protected boolean removeEldestEntry(final Map.Entry<Id, SoftReference<Bitmap>> eldest) {
			return super.size() > maxEntries;
		}

		
		public SoftReference<Bitmap> remove(java.lang.Object key) {
			SoftReference<Bitmap> ref = get(key);
			if (ref != null && ref.get() != null && !ref.get().isRecycled()) {
				ref.get().recycle();
			}
			return super.remove(key);
		}
		
		@Override
		public void clear() {
			
			// do this to ensure recycle called
			//TODO check performance of this
			for (Map.Entry<Id, SoftReference<Bitmap>> entry : entrySet()) {
				cache.remove(entry.getKey());
			}
			
			super.clear();
			
		}
	}

	/**
	 * Hashable data structure for the map key
	 *
	 */
	public static class Id {
		
		public long rowId;
		public String fieldId;
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((fieldId == null) ? 0 : fieldId.hashCode());
			result = prime * result + (int) (rowId ^ (rowId >>> 32));
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
			Id other = (Id) obj;
			if (fieldId == null) {
				if (other.fieldId != null)
					return false;
			} else if (!fieldId.equals(other.fieldId))
				return false;
			if (rowId != other.rowId)
				return false;
			return true;
		}
		
		
	}
	
}
