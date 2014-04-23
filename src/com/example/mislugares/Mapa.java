package com.example.mislugares;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapClickListener,
		OnInfoWindowClickListener {
	private final LatLng UPV = new LatLng(39.481106, -0.340987);
	private GoogleMap mapa;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);
		mapa = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.mapa)).getMap();
		mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mapa.setMyLocationEnabled(true);
		mapa.getUiSettings().setZoomControlsEnabled(false);
		mapa.getUiSettings().setCompassEnabled(true);
		// un escuchador que recoja el evento
		// correspondiente cuando se pulse sobre esta ventana.
		mapa.setOnInfoWindowClickListener(this);
		mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(UPV, 15));
		if (Lugares.vectorLugares.size() > 0) {
			GeoPunto p = Lugares.vectorLugares.get(0).getPosicion();
			mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(
					new LatLng(p.getLatitud(), p.getLongitud()), 12));
		}

		for (Lugar lugar : Lugares.vectorLugares) {
			GeoPunto p = lugar.getPosicion();
			if (p != null && p.getLatitud() != 0) {
				BitmapDrawable iconoDrawable = (BitmapDrawable) getResources()
						.getDrawable(lugar.getTipo().getRecurso());
				Bitmap iGrande = iconoDrawable.getBitmap();
				Bitmap icono = Bitmap.createScaledBitmap(iGrande,
						iGrande.getWidth() / 7, iGrande.getHeight() / 7, false);
				mapa.addMarker(new MarkerOptions()
						.position(new LatLng(p.getLatitud(), p.getLongitud()))
						.title(lugar.getNombre()).snippet(lugar.getDireccion())
						.icon(BitmapDescriptorFactory.fromBitmap(icono)));
			}

		}

		mapa.setOnMapClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onMapClick(LatLng puntoPulsado) {
		mapa.addMarker(new MarkerOptions().position(puntoPulsado).icon(
				BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));

	}

	/*
	 * @Override public void onInfoWindowClick(Marker marker) { for (int id=0;
	 * id<Lugares.vectorLugares.size(); id++){ if (Lugares.vectorLugares.get(
	 * id).getNombre().equals(marker.getTitle())){ Intent intent = new
	 * Intent(this, VistaLugar.class); intent.putExtra("id", (long)id);
	 * startActivity(intent); break; } }
	 * 
	 * }
	 */

	/*
	 * 
	 * desplaza el punto de visualizaci—n a la UPV. A diferencia del uso
	 * anterior, sin cambiar el nivel de zoom que el usuario tenga seleccionado.
	 */

	public void moveCamera(View view) {
		mapa.moveCamera(CameraUpdateFactory.newLatLng(UPV));
	}

	/*
	 * nos desplaza hasta nuestra posici—n actual por medio de una animaci—n
	 * (similar a la que a veces utilizan en el Tele Diario para mostrar un
	 * punto en conflicto).
	 * 
	 * 
	 * getMyLocation() permite obtener la posici—n del dispositivo sin usar el
	 * API Android de posicionamiento. Si usas este mŽtodo verifica siempre que
	 * ya se dispone de una posici—n (!= null) y que has pedido permisos de
	 * localizaci—n.
	 */

	public void animateCamera(View view) {
		if (mapa.getMyLocation() != null)
			mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					mapa.getMyLocation().getLatitude(), mapa.getMyLocation()
							.getLongitude()), 15));
	}

	/*
	 * 
	 * a–ade un nuevo marcador en el centro del mapa que estamos observando
	 * (getCameraPosition()). En este caso usaremos el marcador por defecto, sin
	 * informaci—n adicional.
	 * 
	 * Como hemos indicado onMapClick() ser‡ llamado cuando se pulse sobre el
	 * mapa. Se pasa como par‡metro las coordenadas donde se ha pulsado, que
	 * utilizaremos para a–adir un marcador. Esta vez el marcador por defecto,
	 * es de color amarillo.
	 */

	public void addMarker(View view) {
		mapa.addMarker(new MarkerOptions().position(new LatLng(mapa
				.getCameraPosition().target.latitude,
				mapa.getCameraPosition().target.longitude)));

	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		for (int id = 0; id < Lugares.vectorLugares.size(); id++) {
			if (Lugares.vectorLugares.get(id).getNombre()
					.equals(marker.getTitle())) {
				Intent intent = new Intent(this, VistaLugar.class);
				intent.putExtra("id", (long) id);
				startActivity(intent);
				break;
			}
		}

	}

}
