package studios.darkzen.dictionaryapp.view.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionaryapp.R;

public class ToastUtil {
    public static void customShowToast(Context context, String massage, Drawable toasticon) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.toast_notfount,null);

        ImageView iconView = layout.findViewById(R.id.ic_toast);
        iconView.setImageDrawable(toasticon);

        TextView textView = layout.findViewById(R.id.toastmassage);
        textView.setText(massage);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}
