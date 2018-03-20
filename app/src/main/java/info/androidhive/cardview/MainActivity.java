package info.androidhive.cardview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    RequestQueue queue;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        category=getIntent().getStringExtra("Category");
        initCollapsingToolbar();
        queue= Volley.newRequestQueue(MainActivity.this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(this, albumList);


        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        //prepareAlbums();
        loadRecyclerview(queue);
        try {
            Glide.with(this).load(R.drawable.coverpic).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    /**
     * Adding few albums for testing
     */
    /*
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.one,
                R.drawable.nokia8,
                R.drawable.android,
                R.drawable.kotlin,
                R.drawable.smart,
                R.drawable.asus,
                R.drawable.bagpack,
                R.drawable.stick,
                R.drawable.royal,
                R.drawable.v,
                R.drawable.one};

        Album a = new Album("OnePlus 5T", 1000, covers[0]);
        albumList.add(a);

        a = new Album("Nokia 8", 1250 , covers[1]);
        albumList.add(a);

        a = new Album("Android Programming", 25, covers[2]);
        albumList.add(a);

        a = new Album("Kotlin Programming", 20, covers[3]);
        albumList.add(a);

        a = new Album("Smart TV", 600, covers[4]);
        albumList.add(a);

        a = new Album("Wifi Router", 250, covers[5]);
        albumList.add(a);

        a = new Album("Trekking Bagpack", 125, covers[6]);
        albumList.add(a);
        a = new Album("Hiking Stick", 20, covers[9]);
        albumList.add(a);

        a = new Album("Royal Enfield", 2000, covers[7]);
        albumList.add(a);

        a = new Album("Bajaj V15",900 , covers[8]);
        albumList.add(a);



        adapter.notifyDataSetChanged();
    }
    */
    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    String url="http://lotusworks.16mb.com/DAcredentials.php";
    public void loadRecyclerview(RequestQueue requestQueue){
        final ProgressDialog dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading data");
        dialog.show();
        JSONObject obj=new JSONObject();
        try {
            obj.put("Category",category);
            obj.put("Series","s2e1");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url="http://mrandroid.in/DAcredentials.php?Series=s2e1&Category="+category;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(com.android.volley.Request.Method.GET,
                url, obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response",response.toString());
                        dialog.dismiss();
                        try {
                            JSONArray array=response.getJSONArray("Response");
                            for (int i=0;i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                if(o.getString("result").equals("yes")) {
                                    Log.d("Json", o.toString());
                                    Album album = new Album(
                                            o.getString("ItemID"),
                                            o.getString("Username"),
                                            o.getString("Description"),
                                            o.getString("Mobile"),
                                            o.getString("Location"),
                                            o.getString("Price"),
                                            o.getString("Category"));
                                    albumList.add(album);
                                    Log.d("Album List", albumList.toString());
                                }else{
                                    Toast.makeText(MainActivity.this, "No Product exist in this Category.", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            adapter=new AlbumsAdapter(getApplicationContext(),albumList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        Log.d("Json baby",jsonObjectRequest.toString());

        requestQueue.add(jsonObjectRequest);
    }
}
