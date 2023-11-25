package com.eyupyilmaz.tvseriesapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.text.HtmlCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eyupyilmaz.tvseriesapp.Adapters.ImageSliderAdapter;
import com.eyupyilmaz.tvseriesapp.R;
import com.eyupyilmaz.tvseriesapp.ViewModels.TVSeriesDetailsViewModel;
import com.eyupyilmaz.tvseriesapp.databinding.ActivityTvseriesDetailsBinding;

import java.util.Locale;

public class TVSeriesDetailsActivity extends AppCompatActivity {

    private ActivityTvseriesDetailsBinding activityTvseriesDetailsBinding;
    private TVSeriesDetailsViewModel tvSeriesDetailsViewModel;
    //private BottomSheetDialog episodesBottomSheetDialog;
    //private LayoutEpisodesBottomSheetBinding layoutEpisodesBottomSheetBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityTvseriesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_tvseries_details);
        Initialization();
    }

    private void Initialization(){
        tvSeriesDetailsViewModel = new ViewModelProvider(this).get(TVSeriesDetailsViewModel.class);
        activityTvseriesDetailsBinding.imageBack.setOnClickListener(view -> onBackPressed());
        getTVSeriesDetails();
    }

    private void getTVSeriesDetails(){
        activityTvseriesDetailsBinding.setIsLoading(true);
        String tvSeriesId = String.valueOf(getIntent().getIntExtra("id",-1));
        tvSeriesDetailsViewModel.getTVSeriesDetails(tvSeriesId).observe(
                this, tvSeriesDetailsResponse -> {
                    activityTvseriesDetailsBinding.setIsLoading(false);
                    if (tvSeriesDetailsResponse.getTvSeriesDetails().getPictures() != null){
                        loadImageSlider(tvSeriesDetailsResponse.getTvSeriesDetails().getPictures());
                    }
                    activityTvseriesDetailsBinding.setTvShowImageURL(
                            tvSeriesDetailsResponse.getTvSeriesDetails().getImagePath()
                    );
                    activityTvseriesDetailsBinding.setDescription(
                            String.valueOf(
                                    HtmlCompat.fromHtml(
                                            tvSeriesDetailsResponse.getTvSeriesDetails().getDescription(),
                                            HtmlCompat.FROM_HTML_MODE_LEGACY
                                    )
                            )
                    );
                    activityTvseriesDetailsBinding.textReadMore.setOnClickListener(view -> {
                       if (activityTvseriesDetailsBinding.textReadMore.getText().toString().equals("Read More")){
                           activityTvseriesDetailsBinding.textDescription.setMaxLines(Integer.MAX_VALUE);
                           activityTvseriesDetailsBinding.textDescription.setEllipsize(null);
                           activityTvseriesDetailsBinding.textReadMore.setText("Read Less");
                       }else {
                           activityTvseriesDetailsBinding.textDescription.setMaxLines(4);
                           activityTvseriesDetailsBinding.textDescription.setEllipsize(TextUtils.TruncateAt.END);
                           activityTvseriesDetailsBinding.textReadMore.setText(R.string.read_more);
                       }
                    });
                    activityTvseriesDetailsBinding.setRating(
                            String.format(
                                    Locale.getDefault(),
                                    "%.2f",
                                    Double.parseDouble(tvSeriesDetailsResponse.getTvSeriesDetails().getRating())
                            )
                    );
                    if (tvSeriesDetailsResponse.getTvSeriesDetails().getGenres() != null){
                        activityTvseriesDetailsBinding.setGenre(tvSeriesDetailsResponse.getTvSeriesDetails().getGenres()[0]);
                    }else{
                        activityTvseriesDetailsBinding.setGenre("N/A");
                    }
                    activityTvseriesDetailsBinding.setRuntime(tvSeriesDetailsResponse.getTvSeriesDetails().getRuntime() + " Min");
                    activityTvseriesDetailsBinding.buttonWebsite.setOnClickListener(view -> {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(tvSeriesDetailsResponse.getTvSeriesDetails().getUrl()));
                        startActivity(intent);
                    });
                    /*
                    activityTvseriesDetailsBinding.buttonEpisodes.setOnClickListener(view -> {
                        if (episodesBottomSheetDialog == null){
                            episodesBottomSheetDialog = new BottomSheetDialog(TVSeriesDetailsActivity.this);
                            layoutEpisodesBottomSheetBinding = DataBindingUtil.inflate(
                                    LayoutInflater.from(TVSeriesDetailsActivity.this),
                                    R.layout.layout_episodes_bottom_sheet,
                                    findViewById(R.id.episodesContainer),
                                    false
                            );
                            episodesBottomSheetDialog.setContentView(layoutEpisodesBottomSheetBinding.getRoot());
                            layoutEpisodesBottomSheetBinding.episodesRv.setAdapter(
                                    new EpisodesAdapter(tvSeriesDetailsResponse.getTvSeriesDetails().getEpisodes())
                            );
                            layoutEpisodesBottomSheetBinding.textTitle.setText(
                                    String.format("Episodes | %s", getIntent().getStringExtra("name"))
                            );
                            layoutEpisodesBottomSheetBinding.imageClose.setOnClickListener(view1 -> episodesBottomSheetDialog.dismiss());
                        }
                    });
                     */
                    loadBasicTVSeriesDetails();
                }
        );
    }

    private void loadImageSlider(String[] sliderImages){
        activityTvseriesDetailsBinding.sliderViewPager.setOffscreenPageLimit(1);
        activityTvseriesDetailsBinding.sliderViewPager.setAdapter(new ImageSliderAdapter(sliderImages));
        setupSliderIndicators(sliderImages.length);
        activityTvseriesDetailsBinding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicators(position);
            }
        });
    }

    private void setupSliderIndicators(int count){
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8,0,8,0);
        for (int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.bg_slider_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            activityTvseriesDetailsBinding.layoutSliderIndicators.addView(indicators[i]);
        }
        setCurrentSliderIndicators(0);
    }

    private void setCurrentSliderIndicators(int position){
        int childCount = activityTvseriesDetailsBinding.layoutSliderIndicators.getChildCount();
        for (int i = 0; i < childCount; i++){
            ImageView imageView = (ImageView) activityTvseriesDetailsBinding.layoutSliderIndicators.getChildAt(i);
            if (i == position){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_slider_indicator_active)
                );
            }else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_slider_indicator_inactive)
                );
            }
        }
    }

    private void loadBasicTVSeriesDetails(){
        activityTvseriesDetailsBinding.setTvShowName(getIntent().getStringExtra("name"));
        activityTvseriesDetailsBinding.setNetworkCountry(getIntent().getStringExtra("network") + " (" +
                getIntent().getStringExtra("country") + ")"
        );
        activityTvseriesDetailsBinding.setStatus(getIntent().getStringExtra("status"));
        activityTvseriesDetailsBinding.setStartedDate(getIntent().getStringExtra("startDate"));
    }
}