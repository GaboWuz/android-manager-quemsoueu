package java.androidmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import org.haxe.extension.Extension;
import org.haxe.lime.HaxeObject;

public class Interface extends Extension
{
    private static final String MANAGE_ALL_FILES_ACTION =
        "android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION";

    private static final String SETTINGS_ACTION =
        "android.settings.SETTINGS";

    public static void alert(
        final String title,
        final String msg,
        final String btn,
        final HaxeObject callback
    )
    {
        mainActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                AlertDialog.Builder builder =
                    new AlertDialog.Builder(
                        mainActivity,
                        android.R.style.Theme_DeviceDefault_Dialog_Alert
                    );

                builder.setTitle(title);
                builder.setMessage(msg);

                builder.setPositiveButton(
                    btn,
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(
                            DialogInterface dialog,
                            int which
                        )
                        {
                            if (callback != null)
                            {
                                callback.call(
                                    "onTrigger",
                                    new Object[] {}
                                );
                            }
                        }
                    }
                );

                builder.setCancelable(false);
                builder.show();
            }
        });
    }

    public static void confirm(
        final String title,
        final String msg,
        final String yes,
        final String no,
        final HaxeObject onYes,
        final HaxeObject onNo
    )
    {
        mainActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                AlertDialog.Builder builder =
                    new AlertDialog.Builder(
                        mainActivity,
                        android.R.style.Theme_DeviceDefault_Dialog_Alert
                    );

                builder.setTitle(title);
                builder.setMessage(msg);

                builder.setPositiveButton(
                    yes,
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(
                            DialogInterface dialog,
                            int which
                        )
                        {
                            if (onYes != null)
                            {
                                onYes.call(
                                    "onTrigger",
                                    new Object[] {}
                                );
                            }
                        }
                    }
                );

                builder.setNegativeButton(
                    no,
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(
                            DialogInterface dialog,
                            int which
                        )
                        {
                            if (onNo != null)
                            {
                                onNo.call(
                                    "onTrigger",
                                    new Object[] {}
                                );
                            }
                        }
                    }
                );

                builder.setCancelable(false);
                builder.show();
            }
        });
    }

    public static void navigate(
        final String action,
        final int code
    )
    {
        mainActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Intent intent = new Intent(action);

                    if (MANAGE_ALL_FILES_ACTION.equals(action))
                    {
                        intent.setData(
                            Uri.parse(
                                "package:"
                                + mainContext.getPackageName()
                            )
                        );
                    }

                    mainActivity.startActivityForResult(
                        intent,
                        code
                    );
                }
                catch (Exception error)
                {
                    try
                    {
                        Intent fallback =
                            new Intent(SETTINGS_ACTION);

                        mainActivity.startActivityForResult(
                            fallback,
                            code
                        );
                    }
                    catch (Exception fallbackError)
                    {
                        fallbackError.printStackTrace();
                    }
                }
            }
        });
    }
}