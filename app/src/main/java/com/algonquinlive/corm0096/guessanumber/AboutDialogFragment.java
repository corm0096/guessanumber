package com.algonquinlive.corm0096.guessanumber;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by DC on 2016-09-30.
 */

public class AboutDialogFragment extends DialogFragment
{
    @Override public Dialog onCreateDialog(Bundle savedInstancestate)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.action_about)
            .setMessage(R.string.author)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.cancel();
                }
            });
        return builder.create();
    }
}
