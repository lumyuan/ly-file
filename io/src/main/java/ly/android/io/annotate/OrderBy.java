package ly.android.io.annotate;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.SOURCE;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(SOURCE)
@Target({PARAMETER})
@IntDef(value = {Order.ASCENDING, Order.DESCENDING})
public @interface OrderBy {

}
