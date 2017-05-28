package com.dam2.clickneat.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.dam2.clickneat.R;

/**
 * Created by Pablo on 26/05/2017.
 */

public class MarkerHelper {
    private static final Rect triangleSize = new Rect(0, 0, 40, 20);
    private static final int backgroundColor = Color.WHITE;

    public static class CompactMarkerCreator {

        public static Bitmap create(Bitmap publicationIcon, int iconSize, int padding) {
            Rect size = calculateMarkerSize(iconSize, padding);
            Bitmap marker = Bitmap.createBitmap(size.width(), size.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(marker);

            drawBackground(canvas, size);
            drawPublicationIcon(canvas, publicationIcon, iconSize, padding);

            return marker;
        }

        private static Rect calculateMarkerSize(int iconSize, int padding) {
            return new Rect(
                    0,
                    0,
                    iconSize + (padding * 2),
                    iconSize + (padding * 2) + triangleSize.height()
            );
        }

        private static void drawBackground(Canvas canvas, Rect size) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRect(
                    0,
                    0,
                    size.width(),
                    size.height() - triangleSize.height(),
                    paint
            );

            canvas.save();
            canvas.translate(
                    (size.height() / 2) - (triangleSize.width() / 2),
                    triangleSize.height()
            );

            Path trianglePath = new Path();
            trianglePath.moveTo(0, 0);
            trianglePath.lineTo(triangleSize.width(), 0);
            trianglePath.lineTo(triangleSize.width() / 2, triangleSize.height());
            trianglePath.close();

            canvas.drawPath(trianglePath, paint);

            canvas.restore();
        }

        private static void drawPublicationIcon(Canvas canvas, Bitmap publicationIcon, int iconSize, int padding) {
            Paint paint = new Paint(Paint.DITHER_FLAG);

            canvas.save();
            canvas.translate(padding, padding);

            Rect iconRect = new Rect(0, 0, iconSize, iconSize);
            canvas.drawBitmap(publicationIcon, null, iconRect, paint);

            canvas.restore();
        }
    }

    public static class ExtendedMarkerCreator {

        public static Bitmap create(Activity activity, Bitmap publicationIcon, Bitmap chefIcon, int iconsSize, int padding, String title, int price, String chefName, int rating) {
            String priceText = activity.getString(R.string.label_price) + ": " + price;
            String chefNameText = activity.getString(R.string.label_chef_name) + ": " + chefName;
            String ratingText = activity.getString(R.string.label_rating) + ": " + rating + "/5";

            Paint textPaint = new Paint();
            textPaint.setTypeface(Typeface.DEFAULT);
            textPaint.setColor(Color.BLACK);
            textPaint.setTextSize(20);

            Rect size = calculateMarkerSize(textPaint, iconsSize, padding, title, priceText, chefNameText, ratingText);
            Bitmap marker = Bitmap.createBitmap(size.width(), size.height(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(marker);

            drawBackground(canvas, size);
            drawIcons(canvas, publicationIcon, chefIcon, size, iconsSize, padding);
            drawTexts(canvas, textPaint, title, priceText, chefNameText, ratingText, size, iconsSize, padding);
            return marker;
        }

        private static Rect calculateMarkerSize(Paint textPaint, int iconsSize, int padding, String title, String price, String chefName, String rating) {
            int textWidth = (int) maxTextWidth(
                    textPaint,
                    new String[] {
                        title,
                        price,
                        chefName,
                        rating
                    }
            );
            return new Rect(
                    0,
                    0,
                    iconsSize + textWidth + (padding * 3),
                    (iconsSize * 2) + (padding * 3)
            );
        }

        private static float maxTextWidth(Paint paint, String[] texts) {
            float maxWidth = -1;
            for (String text : texts) {
                float width = paint.measureText(text);
                if(width > maxWidth) {
                    maxWidth = width;
                }
            }
            return maxWidth;
        }

        private static void drawBackground(Canvas canvas, Rect size) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(backgroundColor);
            paint.setStyle(Paint.Style.FILL);

            canvas.drawRect(
                    0,
                    0,
                    size.width(),
                    size.height() - triangleSize.height(),
                    paint
            );

            canvas.save();
            canvas.translate(
                    (size.height() / 2) - (triangleSize.width() / 2),
                    triangleSize.height()
            );

            Path trianglePath = new Path();
            trianglePath.moveTo(0, 0);
            trianglePath.lineTo(triangleSize.width(), 0);
            trianglePath.lineTo(triangleSize.width() / 2, triangleSize.height());
            trianglePath.close();

            canvas.drawPath(trianglePath, paint);

            canvas.restore();
        }

        private static void drawIcons(Canvas canvas, Bitmap publicationIcon, Bitmap chefIcon, Rect size, int iconsSize, int padding) {
            Paint paint = new Paint(Paint.DITHER_FLAG);
            Rect iconRect = new Rect(0, 0, iconsSize, iconsSize);

            canvas.save();
            canvas.translate(padding, padding);

            canvas.drawBitmap(publicationIcon, null, iconRect, paint);

            canvas.restore();

            canvas.save();
            canvas.translate(padding, (padding * 2) + iconsSize);

            canvas.drawBitmap(chefIcon, null, iconRect, paint);

            canvas.restore();
        }

        private static void drawTexts(Canvas canvas, Paint textPaint, String title, String priceText, String chefNameText, String ratingText, Rect size, int iconsSize, int padding) {
            canvas.save();
            canvas.translate((padding * 2) + iconsSize, padding);

            canvas.drawText(title, 0, 0, textPaint);
            canvas.drawText(priceText, 0, 20, textPaint);

            canvas.restore();

            canvas.save();
            canvas.translate((padding * 2) + iconsSize, (padding * 2) + iconsSize);

            canvas.drawText(chefNameText, 0, 0, textPaint);
            canvas.drawText(ratingText, 0, 0, textPaint);

            canvas.restore();
        }
    }
}
