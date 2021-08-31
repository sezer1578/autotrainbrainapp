package com.hms.atbotizmozel.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.hms.atbotizmozel.R;
import com.hms.atbotizmozel.data.call.AtbCalls;
import com.hms.atbotizmozel.data.model.AtbActivityModel;
import com.hms.atbotizmozel.data.model.UserModel;
import com.hms.atbotizmozel.data.model.response.BaseResponse;
import com.hms.atbotizmozel.util.ApplicationData;
import com.hms.atbotizmozel.util.ErrorUtils;
import com.hms.atbotizmozel.util.PrefUtils;


import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;

import org.tensorflow.lite.Interpreter;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
//import org.tensorflow.lite.Interpreter;


public class ShowReportActivity extends AppCompatActivity {

    private static final String TAG = "TFLITE";
    private static final String MODEL_PATH = "converted_model.tflite";
    private static final int NUM_LITE_THREADS = 4;
    private String predict="";
    private float[] input= new float[] {-0.640578944f,-0.238122443f,-0.521531992f,0.168872661f,-1.079413675f,-0.842489829f,-1.142405678f,-1.502234717f,-1.394181392f,-1.579734412f,0.313368752f,-1.519529709f,-0.990820273f,0.997443299f,-0.638315736f,-0.302758671f,-0.087681931f,-0.50533243f,-1.362855078f,-1.254301303f,-1.113771692f,-1f,-1.286370818f,-1.341144972f,-0.135034062f,-1.755765434f,-0.046360298f,-0.298542316f,-0.29835103f,0.196493525f,0.223945765f,-0.117376956f,0.421015103f,0.892303636f,-1.072458806f,-1.238827017f,-1.505552505f,-1.444447739f,-1.658374795f,-1.582110637f,1.325721373f,-1.789866018f,0.814289344f,3.043844981f,0.059683235f,-0.428416361f,0.005964658f,-0.055723591f,-0.917126163f,-1.087144098f,-1.252620506f,-1.17981582f,-1.313440301f,-1.360763505f,0.653111548f,-1.349252427f,0.415589261f,1.843834786f,-0.151690632f,-0.578023159f,-0.199331083f,-0.398610221f,-0.863459575f,-1.036883806f,-1.205124089f,-0.976396196f,-1.195621127f,-1.072274602f,0.602643446f,-1.124926384f};
    private float[][] type = new float[1][1];




    private int[][] ageGroups = new int[][]{
            {0, 6},
            {7, 9},
            {10, 16},
            {17, 25},
            {26, 39},
            {40, 100}
    };

    //AF3, F3, F7, FC5, T7, P7, O1, O2, P8, T8, FC6, F4, F8, AF4


    private double[][] normData = new double[][]{
            {8.997173, 6.484346, 7.765183, 3.993770, 2.844660, 2.775916, 4.53534, 7.783403, 9.127225, 9.854712, 9.001937, 7.101832, 10.438639, 10.340471},
            {8.025952, 5.859845, 6.998042, 3.867749, 2.567467, 2.075743, 3.084368, 5.857763, 7.465416, 8.153756, 7.952003, 6.167924, 9.880340, 8.408698},
            {6.416349, 3.699387, 6.384698, 3.055529, 2.367235, 1.670006, 2.014711, 3.498200, 6.450901, 7.201697, 6.306909, 4.283311, 8.774152, 5.840886},
            {3.273973, 1.357808, 3.102603, 1.439324, 1.342192, 0.796986, 1.087945, 1.582500, 6.169444, 6.915833, 3.525616, 2.450137, 5.572466, 4.001644},
            {4.881660, 1.627221, 3.457469, 1.509955, 1.111454, 0.833835, 0.990675, 1.563505, 6.219907, 6.494905, 3.530752, 2.158726, 5.124680, 4.429426},
            {4.266287, 1.845509, 3.307617, 1.549929, 1.110416, 0.933575, 1.208479, 1.893722, 3.690343, 4.447397, 3.439994, 2.011139, 5.074634, 4.619975}
    };

    private double[][] normdata_stddev = new double[][]{
            {3.274403, 3.547172, 3.287784, 2.290594, 2.109214, 1.909644, 3.22475, 4.191687, 3.413088, 3.679748, 3.013532, 3.765746, 3.288471, 3.292006},
            {3.367237, 2.800232, 3.562758, 2.273404, 2.177051, 1.924066, 2.381730, 3.567851, 3.401789, 2.983450, 2.741893, 3.155349, 3.688255, 3.583804},
            {4.162405, 2.769109, 4.399918, 2.335908, 2.165282, 1.922879, 2.551380, 2.994339, 4.627017, 4.438529, 3.488194, 2.785935, 4.344029, 3.347673},
            {2.851095, 1.124936, 2.212800, 1.251097, 1.386550, 0.767707, 1.012235, 1.733666, 5.709992, 6.099177, 2.484526, 2.355292, 3.395776, 3.132318},
            {3.749459, 1.353313, 3.124273, 1.123862, 0.887853, 0.826042, 0.915263, 2.496758, 5.636613, 7.800383, 4.015353, 1.528887, 4.248285, 3.893173},
            {3.230249, 1.655948, 2.658227, 1.381825, 1.005942, 0.984614, 1.097684, 1.617827, 3.683388, 3.646259, 2.720988, 1.783652, 3.688694, 3.632665}
    };

    private double[][] normdata_alpha = new double[][]{
            {3.322880, 2.777382, 2.449372, 1.672304, 1.011316, 1.247696, 1.859895, 3.572775, 4.112827, 4.378743, 3.922356, 2.938691, 3.781832, 3.848796},
            {3.174877, 2.538906, 2.373020, 1.654418, 1.149319, 1.075999, 1.828816, 3.145295, 3.867142, 3.937902, 3.697801, 2.694401, 3.852433, 3.360676},
            {2.901209, 1.846380, 1.846380, 2.361801, 1.406303, 1.043562, 0.786168, 1.886196, 3.153978, 3.746552, 4.005842, 3.164662, 2.168003, 3.804004},
            {1.310821, 0.737157, 1.209681, 0.722687, 0.645346, 0.397518, 0.839236, 1.771135, 3.579118, 4.111192, 1.930788, 1.355745, 2.551472, 1.680407},
            {1.527872, 0.800126, 1.117949, 0.626702, 0.463609, 0.375795, 0.535133, 1.219383, 2.951868, 3.708371, 1.907268, 1.017289, 2.332781, 1.670194},
            {1.566517, 0.950637, 1.175189, 0.704910, 0.475249, 0.421259, 0.661118, 1.181012, 1.937242, 2.483599, 1.787551, 1.311984, 2.151669, 1.854409}
    };

    private double[][] normdata_beta1 = new double[][]{
            {1.359843, 1.052251, 1.117696, 0.817539, 0.679215, 0.894869, 0.803874, 1.370209, 1.993717, 2.037853, 1.652513, 1.094084, 1.582199, 1.568115},
            {1.379211, 1.118823, 1.118984, 0.863176, 0.761790, 0.676671, 0.843931, 1.408464, 1.903383, 1.990480, 1.687073, 1.164089, 1.677008, 1.456802},
            {1.441297, 0.926847, 0.926847, 1.177235, 0.789685, 0.742173, 0.509225, 0.879186, 1.486164, 1.997443, 2.219534, 1.574341, 1.097490, 1.841536},
            {0.605892, 0.374643, 0.597340, 0.408908, 0.407314, 0.228679, 0.406977, 0.833677, 1.813042, 2.147576, 0.981174, 0.651649, 1.271865, 0.786106},
            {0.764620, 0.469376, 0.623712, 0.422351, 0.355264, 0.252056, 0.342109, 0.727044, 2.010985, 2.539060, 1.150622, 0.566987, 1.334508, 0.847160},
            {0.774163, 0.554151, 0.678586, 0.456001, 0.337157, 0.268006, 0.419901, 0.767924, 1.222892, 1.646615, 1.105483, 0.767716, 1.260361, 0.933005}
    };

    private double[][] normdata_beta2 = new double[][]{
            {0.906230, 0.625851, 0.807539, 0.629424, 0.710105, 0.880000, 0.452139, 0.717906, 1.513089, 1.640733, 1.226859, 0.638429, 1.089263, 1.089738},
            {0.872485, 0.646823, 0.811518, 0.657133, 0.789349, 0.552922, 0.479798, 0.713798, 1.244654, 1.502297, 1.166819, 0.673310, 1.100602, 0.929757},
            {0.879919, 0.515800, 0.515800, 0.835350, 0.587270, 0.748873, 0.382429, 0.469376, 0.734697, 1.225936, 1.523068, 1.031680, 0.636206, 1.224987},
            {0.365541, 0.228771, 0.418063, 0.302416, 0.343663, 0.152536, 0.236382, 0.429437, 1.035789, 1.417133, 0.666490, 0.381347, 0.811097, 0.484873},
            {0.550333, 0.330819, 0.507778, 0.360927, 0.364092, 0.205262, 0.225796, 0.480382, 1.594199, 2.248161, 0.869771, 0.365132, 0.966773, 0.589709},
            {0.555133, 0.371314, 0.505748, 0.375148, 0.258351, 0.187232, 0.255281, 0.473141, 0.765129, 1.075907, 0.768175, 0.508286, 0.828710, 0.652670}
    };

    private double[][] normdata_alpha_stddev = new double[][]{
            {1.307403, 1.574355, 1.051558, 0.927266, 0.649862, 0.970718, 1.364471, 2.217378, 1.625547, 1.595358, 1.387808, 1.656057, 1.198605, 1.345628},
            {1.416052, 1.240362, 1.230483, 0.867013, 0.798849, 0.814022, 1.287769, 2.082235, 1.999473, 1.632329, 1.296729, 1.417613, 1.560776, 1.484286},
            {2.195166, 1.441346, 1.441346, 1.705414, 1.017205, 0.808069, 0.705861, 1.886196, 2.451842, 2.666790, 2.487091, 1.779658, 1.466837, 1.956185},
            {1.020667, 0.572417, 0.848327, 0.594573, 0.621462, 0.387930, 0.851311, 1.541355, 3.384718, 3.819282, 1.220864, 1.150199, 1.484291, 1.090508},
            {0.992023, 0.626973, 0.956798, 0.466725, 0.340259, 0.307722, 0.492305, 1.121914, 2.597292, 3.925396, 1.529673, 0.693910, 1.559484, 1.232997},
            {1.022853, 0.693102, 0.839362, 0.549616, 0.320968, 0.358067, 0.517852, 0.866418, 1.437475, 1.552416, 1.114900, 0.822974, 1.188008, 1.195735}
    };

    private double[][] normdata_beta1_stddev =new double[][]{
            {0.593604, 0.621864, 0.526106, 0.471486, 0.517131, 0.997642, 0.576484, 0.796022, 1.040088, 0.860963, 0.618137, 0.590861, 0.565554, 0.606672},
            {0.622049, 0.564461, 0.595171, 0.467621, 0.553722, 0.589470, 0.560747, 0.933858, 1.190134, 0.906183, 0.645359, 0.681964, 0.739671, 0.632717},
            {1.177462, 0.728538, 0.728538, 0.913595, 0.570799, 0.580326, 0.446847, 0.693548, 0.987122, 1.382842, 1.338379, 0.881317, 0.801458, 0.989759},
            {0.454787, 0.293197, 0.330685, 0.310482, 0.323255, 0.179710, 0.296257, 0.603167, 1.728532, 1.748752, 0.543217, 0.498874, 0.636641, 0.534341},
            {0.474255, 0.367184, 0.439153, 0.319834, 0.302926, 0.204969, 0.293778, 0.537059, 1.970411, 3.032584, 0.835024, 0.388092, 0.876863, 0.563634},
            {0.436567, 0.358776, 0.426885, 0.345738, 0.231782, 0.208169, 0.316294, 0.467144, 0.877012, 1.028341, 0.600869, 0.432187, 0.618089, 0.552897}
    };

    private double[][] normdata_beta2_stddev = new double[][]{
            {0.528335, 0.418647, 0.474379, 0.436874, 0.709019, 1.311258, 0.359941, 0.435839, 1.119098, 0.976134, 0.636959, 0.406616, 0.517469, 0.604785},
            {0.435134, 0.356211, 0.509981, 0.391653, 0.706965, 0.663171, 0.349600, 0.489204, 0.917496, 0.862597, 0.548320, 0.411466, 0.562950, 0.473955},
            {0.712377, 0.405474, 0.405474, 0.736083, 0.457696, 0.697604, 0.395179, 0.422553, 0.586528, 1.039091, 1.159602, 0.662628, 0.526394, 0.885666},
            {0.281684, 0.190193, 0.270945, 0.267482, 0.303490, 0.120004, 0.177483, 0.299622, 1.040105, 1.412465, 0.446903, 0.286659, 0.465048, 0.343954},
            {0.353467, 0.276085, 0.420002, 0.336040, 0.436316, 0.208943, 0.205139, 0.401272, 1.766681, 3.258113, 0.696220, 0.244327, 0.760776, 0.449207},
            {0.360885, 0.254265, 0.349892, 0.334405, 0.194885, 0.148926, 0.193312, 0.298754, 0.541832, 0.650919, 0.367282, 0.286615, 0.405604, 0.377088}
    };

    private double[][] normdata_gamma_stddev= new double[][]{
            {0.487057, 0.269082, 0.426871, 0.383311, 0.705351, 1.466778, 0.270005, 0.338272, 1.166232, 0.930021, 0.603948, 0.274573, 0.468678, 0.546577},
            {0.336704, 0.228642, 0.445978, 0.352544, 0.734647, 0.483831, 0.228492, 0.299190, 0.725280, 0.746982, 0.441809, 0.255938, 0.369069, 0.331130},
            {0.442266, 0.260583, 0.260583, 0.608486, 0.423371, 0.716517, 0.322161, 0.296092, 0.367519, 0.844652, 0.852137, 0.479297, 0.266773, 0.680897},
            {0.232486, 0.125397, 0.291047, 0.281705, 0.276240, 0.100837, 0.167488, 0.264416, 0.847234, 1.375265, 0.532899, 0.204630, 0.531023, 0.289574},
            {0.450583, 0.253848, 0.536634, 0.353770, 0.564190, 0.224087, 0.210331, 0.392044, 1.553813, 23.484056, 0.701664, 0.221920, 0.813616, 0.453640},
            {0.312247, 0.159171, 0.374507, 0.344490, 0.206002, 0.115250, 0.129480, 0.212233, 0.385662, 0.534475, 0.302662, 0.209151, 0.348629, 0.253728}
    };



    private double[][] normdata_gamma = new double[][]{
            {0.623665, 0.353370, 0.604607, 0.479372, 0.614346, 0.809476, 0.307819, 0.469043, 1.176702, 1.223665, 0.907487, 0.371414, 0.764868, 0.748168},
            {0.522994, 0.341904, 0.584154, 0.480173, 0.672454, 0.396579, 0.308473, 0.403806, 0.834997, 1.066234, 0.765191, 0.365417, 0.665540, 0.548666},
            {0.536199, 0.299528, 0.299528, 0.601719, 0.444391, 0.675611, 0.298173, 0.317013, 0.442906, 0.824715, 1.018833, 0.663938, 0.339231, 0.785959},
            {0.277558, 0.150537, 0.354481, 0.264986, 0.291876, 0.131387, 0.186585, 0.307305, 0.762903, 1.136388, 0.573256, 0.255622, 0.644533, 0.346458},
            {0.480754, 0.254747, 0.497844, 0.336691, 0.399473, 0.172569, 0.201654, 0.401200, 1.332199, 5.731857, 0.768925, 0.276362, 0.825306, 0.475984},
            {0.381189, 0.226528, 0.402997, 0.308720, 0.219721, 0.131662, 0.164035, 0.289843, 0.513716, 0.740400, 0.532008, 0.309581, 0.566189, 0.409884}
    };




    // deneme
    private double[] alpha = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private double[] beta1 = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private double[] beta2 = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private double[] gamma = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private double[] theta = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    private String[] channelNames = {"AF3", "F3", "F7", "FC5", "T7", "P7", "O1", "O2", "P8", "T8", "FC6", "F8", "F4", "AF4"};

    //private Interpreter tflite;
    private Interpreter tflite;
    @BindView(R.id.barChart)
    BarChart barChart;
    @BindView(R.id.diagnoseResult)
    TextView diagnoseResult;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.reportSpinner)
    AppCompatSpinner reportSpinner;
    @BindView(R.id.typeSpinner)
    AppCompatSpinner typeSpinner;
    @BindView(R.id.activity)
    TextView activityName;
    @BindView(R.id.protocol)
    TextView protocolName;
    @BindView(R.id.mode)
    TextView modeName;

    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    int session;
    long sessionDate;
    int age;
    //int tt;
    int ageindex;
    String str;



    private ProgressDialog progressDialog;
    private UserModel userModel;
    // private DecimalFormat yAxisFormatter;


    public MappedByteBuffer loadModelFile(AssetManager assetManager) throws IOException {
        try (AssetFileDescriptor fileDescriptor = assetManager.openFd(MODEL_PATH);
             FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor())) {
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        }
    }

    public static double sigmoid(double x)
    { return(1/(1+ Math.pow(Math.E,(-1*x))));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ByteBuffer buffer = loadModelFile(this.getAssets());
            Interpreter.Options opt = new Interpreter.Options();
            opt.setNumThreads(NUM_LITE_THREADS);
            tflite = new Interpreter(buffer, opt);
            Log.v(TAG, "TFLite model loaded.");
        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage());
        }

        int  random= (int) (Math.random() * 20 + 1);
        if (random==10) {

            String url = "https://docs.google.com/forms/d/e/1FAIpQLScvn8wzZ1c3chNPH9Uq23Uruw3BFfXipQmsffxZrUdOEi83BA/viewform";
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        progressDialog = new ProgressDialog(ShowReportActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage(getText(R.string.waitMessage));
        progressDialog.setTitle(getText(R.string.waitTitle));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progressDialog.getProgress() <= progressDialog
                            .getMax()) {
                        Thread.sleep(200);
                        handle.sendMessage(handle.obtainMessage());
                        if (progressDialog.getProgress() == progressDialog
                                .getMax()) {
                            progressDialog.dismiss();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        setContentView(R.layout.activity_show_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        ApplicationData.userModel = PrefUtils.with(this).getUserModel();
        session = getIntent().getIntExtra("session", 0);
        age= userModel.age;

        load();

        List<String> types = new ArrayList<>();
        types.add(getString(R.string.absolute));
        types.add(getString(R.string.relative));
        ArrayAdapter<String> typesAdapter = new ArrayAdapter<>(this, R.layout.item_spinner_small, types);
        typesAdapter.setDropDownViewResource(R.layout.item_spinner_small);
        typeSpinner.setAdapter(typesAdapter);

        List<String> reports = new ArrayList<>();
        reports.add(getString(R.string.theta));
        reports.add(getString(R.string.alpha));
        reports.add(getString(R.string.beta1));
        reports.add(getString(R.string.beta2));
        reports.add(getString(R.string.gamma));
        ArrayAdapter<String> reportsAdapter = new ArrayAdapter<>(this, R.layout.item_spinner_small, reports);
        reportsAdapter.setDropDownViewResource(R.layout.item_spinner_small);
        reportSpinner.setAdapter(reportsAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.incrementProgressBy(1);
        }
    };

    private void load() {
        AtbCalls.getAtbActivityBySession(this, userModel, session, new Callback<BaseResponse<List<AtbActivityModel>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<AtbActivityModel>>> call, retrofit2.Response<BaseResponse<List<AtbActivityModel>>> response) {
                if (ErrorUtils.isSuccess(ShowReportActivity.this, response)) {
                    List<AtbActivityModel> models = response.body().data;
                    for (int i = 0; i < models.size(); i++) {
                        sessionDate = models.get(i).date;
                        theta[i] = models.get(i).theta;
                        input[i] = (float) ((theta[i]-normData[ageindex][i])/normdata_stddev[ageindex][i]);
                        alpha[i] = models.get(i).alpha;
                        input[i+14] = (float) ((alpha[i]-normdata_alpha[ageindex][i])/normdata_alpha_stddev[ageindex][i]);
                        beta1[i] = models.get(i).beta1;
                        input[i+28] = (float) ((beta1[i]-normdata_beta1[ageindex][i])/normdata_beta1_stddev[ageindex][i]);
                        beta2[i] = models.get(i).beta2;
                        input[i+42] = (float) ((beta2[i]-normdata_beta2[ageindex][i])/normdata_beta2_stddev[ageindex][i]);
                        gamma[i] = models.get(i).gamma;
                        input[i+56] = (float) ((gamma[i]-normdata_gamma[ageindex][i])/normdata_gamma_stddev[ageindex][i]);
                    }
                    try {
                        try {
                            Calendar dateOfBirth = Calendar.getInstance();
                            dateOfBirth.setTime(format.parse(userModel.birthdate));
                            Calendar today = Calendar.getInstance();
                            today.setTimeInMillis(sessionDate*1000);

                            //tt = today.get(Calendar.YEAR);
                            age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
                            Log.e("dene" , String.valueOf(sessionDate));
                            Log.e("dene" , String.valueOf(today.get(Calendar.YEAR) ));
                            Log.e("dene" , String.valueOf(dateOfBirth.get(Calendar.YEAR)));
                            Log.e("dene" , String.valueOf(age));

                        } catch (Exception e) {
                            age = 1;
                        }

                        ageindex = 3;
                        for (int ind1 = 0; ind1 < 6; ind1++)
                            if (age >= ageGroups[ind1][0] && age <= ageGroups[ind1][1])
                                ageindex = ind1;

                    if (models.get(0).protocol!="" )
                            protocolName.setText(models.get(0).protocol);
                        else protocolName.setText("");
                        if (models.get(0).mode!="" )
                            modeName.setText(models.get(0).mode);
                        else protocolName.setText("");
                        if (models.get(0).activity!="" )
                            activityName.setText(models.get(0).activity);
                        else activityName.setText("");


                        match();
                        loadChart();
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<AtbActivityModel>>> call, Throwable t) {
                ErrorUtils.parseThrowable(ShowReportActivity.this, t);
            }
        });
    }


    private void match() {
        str = predict;
        boolean visual = true, auditory = true, manager = true, per_care = true;

        for(int i=0;i<14;i++)
            if (theta[i]> normData[ageindex][i]) {
                str += "\n- " + getString(R.string.diagnose);
                break;
            }

        if (theta[6] != 0 && theta[6] > normData[ageindex][6] ||
                theta[5] != 0 && theta[5] > normData[ageindex][5]) {
            visual = false;
            str += "\n- " + getString(R.string.diagnose);

        }
        if (theta[4] != 0 && theta[4] > normData[ageindex][4] ||
                theta[3] != 0 && theta[3] > normData[ageindex][3]
        ) {
            auditory = false;
            str += "\n- " + getString(R.string.diagnose_auditory);
        }
        if (theta[0] != 0 && theta[0] > normData[ageindex][0]) {
            manager = false;
            str += "\n- " + getString(R.string.diagnose_manager);
        }

        if ((theta[8] != 0 && theta[8] > normData[ageindex][8] + 2 * normdata_stddev[ageindex][8]) ||
                (theta[7] != 0 && theta[7] > normData[ageindex][7] + 2 * normdata_stddev[ageindex][7]) ||
                (theta[9] != 0 && theta[9] > normData[ageindex][9] + 2 * normdata_stddev[ageindex][9]))
        {
            per_care = false;
            str += "\n- " + getString(R.string.dignose_care);
        }

        if (theta[12] != 0 && theta[12] > normData[ageindex][12] + 2 * normdata_stddev[ageindex][12]) {
            str += "\n- " + getString(R.string.diagnose_anxiety);
        }
        /*if (theta[0] != 0 && theta[0] > (normData[ageindex][0] + 2 * normdata_stddev[ageindex][0])) {
            str += "\n- " + getString(R.string.diagnose_AD);
        }
        if (theta[2] != 0 && (theta[2] > (normData[ageindex][2] + 2 * normdata_stddev[ageindex][2]) || theta[3] > (normData[ageindex][3] + 2 * normdata_stddev[ageindex][3]))) {
            str += "\n- " + getString(R.string.diagnose_LD);
        } else if (theta[4] != 0 && (theta[4] > (normData[ageindex][4] + 2 * normdata_stddev[ageindex][4]) || theta[5] > (normData[ageindex][5] + 2 * normdata_stddev[ageindex][5]))) {
            str += "\n- " + getString(R.string.diagnose_LD);
        } else if (theta[6] != 0 && theta[5] != 0 && theta[7] != 0 && (theta[6] / theta[5] > 2 || theta[7] / theta[6] > 2)) {
            str += "\n- " + getString(R.string.diagnose_Focus);
        } else if (theta[6] != 0 && (theta[6] > (normData[ageindex][6] + 2 * normdata_stddev[ageindex][6]) || theta[7] > (normData[ageindex][7] + 2 * normdata_stddev[ageindex][7]))) {
            str += "\n- " + getString(R.string.diagnose_LD);
        } else if (theta[5] != 0 && (theta[5] > (normData[ageindex][5] + 2 * normdata_stddev[ageindex][5]))) {
            str += "\n- " + getString(R.string.diagnose_LD);
        }*/

        Double theta1 = (theta[0] + theta[1] + theta[2] + theta[3] + theta[4] + theta[5] + theta[6]) / 7;
        Double theta2 = (theta[7] + theta[8] + theta[9] + theta[10] + theta[11] + theta[12] + theta[13]) / 7;

        if (visual && auditory && manager && theta1>0 && theta2>0) {
            for (int i = 0; i < 14; i++)
                if (gamma[i]< normdata_gamma[ageindex][i]) {
                    str += "\n- " + getString(R.string.diagnosepd);
                    break;
                }
             str += "\n- " + getString(R.string.diagnose_gifted);
        }
        if (theta1 != 0 && theta2 != 0 && theta1 < theta2) {
            str += "\n- " + getString(R.string.diagnose_LB);
        } else if (theta1 != 0 && theta2 != 0) {
            str += "\n- " + getString(R.string.diagnose_RB);
        }
        //str += Integer.toString(age) + " " + Integer.toString(tt) + " " + Long.toString(sessionDate);
        diagnoseResult.setText(str);
    }

    private void loadChart() {
        progressBar.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);

        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        Legend l = barChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                try {
                    return channelNames[(int) value];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        /*leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return yAxisFormatter.format(value);
            }
        });*/
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(35f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        barChart.getAxisRight().setEnabled(false);

        ArrayList<BarEntry> set1Values = new ArrayList<>();
        ArrayList<BarEntry> set2Values = new ArrayList<>();
        setChartValues(set1Values, set2Values);

        BarDataSet set1, set2;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            BarDataSet barDataSet1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            barDataSet1.setLabel(reportSpinner.getSelectedItem().toString());
            set1 = barDataSet1;
            set2 = (BarDataSet) barChart.getData().getDataSetByIndex(1);
            set1.setValues(set1Values);
            set2.setValues(set2Values);
            set1.setDrawValues(false);
            set2.setDrawValues(false);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(set1Values, reportSpinner.getSelectedItem().toString());
            set1.setColor(ContextCompat.getColor(this, R.color.red));
            set2 = new BarDataSet(set2Values, getString(R.string.norm_data));
            set2.setColor(ContextCompat.getColor(this, R.color.green));
            set1.setDrawValues(false);
            set2.setDrawValues(false);
            BarData data = new BarData(set1, set2);
            barChart.setData(data);
        }
        barChart.getData().setValueFormatter(new DefaultValueFormatter(2));
        float groupSpace = 0.1f;
        float barSpace = 0.05f;
        float barWidth = 0.4f;

        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(barChart.getBarData().getGroupWidth(groupSpace, barSpace) * 14);
        barChart.groupBars(0, groupSpace, barSpace);
        barChart.invalidate();
    }


    private void setChartValues(ArrayList<BarEntry> set1Values, ArrayList<BarEntry> set2Values) {
        double sum, sum_norm;
        for (int i = 0; i < 14; i++) {
            sum = (theta[i] + alpha[i] + beta1[i] + beta2[i] + gamma[i]);
            sum_norm = normData[ageindex][i] + normdata_alpha[ageindex][i] + normdata_beta1[ageindex][i] + normdata_beta2[ageindex][i] + normdata_gamma[ageindex][i];
            switch (reportSpinner.getSelectedItemPosition()) {
                case 0:
                    if (typeSpinner.getSelectedItemPosition() == 0) {
                        set1Values.add(new BarEntry(i, (float) theta[i]));
                        set2Values.add(new BarEntry(i, (float) normData[ageindex][i]));
                    } else {
                        set1Values.add(new BarEntry(i, (float) (theta[i] / sum)));
                        set2Values.add(new BarEntry(i, (float) (normData[ageindex][i] / sum_norm)));
                    }

                    break;

                case 1:
                    if (typeSpinner.getSelectedItemPosition() == 0) {
                        set1Values.add(new BarEntry(i, (float) alpha[i]));
                        set2Values.add(new BarEntry(i, (float) normdata_alpha[ageindex][i]));
                    } else {
                        set1Values.add(new BarEntry(i, (float) (alpha[i] / sum)));
                        set2Values.add(new BarEntry(i, (float) (normdata_alpha[ageindex][i] / sum_norm)));
                    }

                    break;
                case 2:
                    if (typeSpinner.getSelectedItemPosition() == 0) {
                        set1Values.add(new BarEntry(i, (float) beta1[i]));
                        set2Values.add(new BarEntry(i, (float) normdata_beta1[ageindex][i]));
                    } else {
                        set1Values.add(new BarEntry(i, (float) (beta1[i] / sum)));
                        set2Values.add(new BarEntry(i, (float) (normdata_beta1[ageindex][i] / sum_norm)));
                    }

                    break;
                case 3:
                    if (typeSpinner.getSelectedItemPosition() == 0) {
                        set1Values.add(new BarEntry(i, (float) beta2[i]));
                        set2Values.add(new BarEntry(i, (float) normdata_beta2[ageindex][i]));
                    } else {
                        set1Values.add(new BarEntry(i, (float) (beta2[i] / sum)));
                        set2Values.add(new BarEntry(i, (float) (normdata_beta2[ageindex][i] / sum_norm)));
                    }

                    break;
                case 4:
                    if (typeSpinner.getSelectedItemPosition() == 0) {
                        set1Values.add(new BarEntry(i, (float) gamma[i]));
                        set2Values.add(new BarEntry(i, (float) normdata_gamma[ageindex][i]));
                    } else {
                        set1Values.add(new BarEntry(i, (float) (gamma[i] / sum)));
                        set2Values.add(new BarEntry(i, (float) (normdata_gamma[ageindex][i] / sum_norm)));
                    }

                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.profile:
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_logout:
                PrefUtils.with(ShowReportActivity.this).savePassword("");
                PrefUtils.with(ShowReportActivity.this).saveUserName("");
                PrefUtils.with(ShowReportActivity.this).removeUserModel();
                PrefUtils.with(ShowReportActivity.this).clear();
                Intent i = new Intent(getBaseContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
