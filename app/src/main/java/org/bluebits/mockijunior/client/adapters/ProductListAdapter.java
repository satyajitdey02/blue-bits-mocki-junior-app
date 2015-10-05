/**
 * 
 */
package org.bluebits.mockijunior.client.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.bluebits.mockijunior.ApplicationContext;
import org.bluebits.mockijunior.R;
import org.bluebits.mockijunior.client.model.OrderProduct;
import org.bluebits.mockijunior.client.model.Product;


/**
 * @author satyajit
 * 
 */
public class ProductListAdapter extends ArrayAdapter<Product> {

	private final List<Product> list;
	private final Activity context;
	private final ApplicationContext appContext;

	public ProductListAdapter(Activity context, List<Product> list) {
		super(context, R.layout.layout_oc_product_list_item, list);
		this.context = context;
		this.appContext = (ApplicationContext) context.getApplicationContext();
		this.list = list;
	}

	static class ViewHolder {
		protected TextView lblProduct;
		protected EditText txtQty;
	}

	@Override     
	public View getView(int position, View convertView, ViewGroup parent) {        
		View row = null;         
		if (convertView == null) {            
			LayoutInflater inflator = context.getLayoutInflater(); 
			row = inflator.inflate(R.layout.layout_oc_product_list_item, null); 
			final ViewHolder holder = new ViewHolder(); 
			holder.lblProduct = (TextView) row.findViewById(R.id.product);          
			holder.txtQty = (EditText) row.findViewById(R.id.qty);        
			holder.txtQty.addTextChangedListener(new TextWatcher() {  
				public void onTextChanged(CharSequence s, int start, int before, int count) {      
					Product element=(Product)holder.txtQty.getTag();
					String qty = s.toString();
					element.setQty(TextUtils.isEmpty(qty) || "0".equals(qty) ? 0 :Integer.parseInt(qty)); 
					appContext.getUserGeneratedData().setOrderProducts(getOrderProducts());
				}        
				public void beforeTextChanged(CharSequence s, int start, int count,int after) {       
				
				}      
				public void afterTextChanged(Editable s) {                              
				
				}
			}); 
			
			holder.txtQty.setTag(list.get(position));
			row.setTag(holder);   
		} else {
			row = convertView; 
			((ViewHolder) row.getTag()).txtQty.setTag(list.get(position));        
		}                 
		
		ViewHolder holder = (ViewHolder) row.getTag();  
		holder.lblProduct.setText(list.get(position).getName());  
		holder.txtQty.setText(String.valueOf(list.get(position).getQty()));  
		
		return row;
	}
	
	public List<OrderProduct> getOrderProducts() {
		List<OrderProduct> orderProducts = null;

		if (list != null && list.size() > 0) {
			orderProducts = new ArrayList<OrderProduct>();
			for (Product product : list) {
				if (product.getQty() > 0) {
					OrderProduct orderProduct = new OrderProduct(
							product.getId(), product.getQty());
					orderProducts.add(orderProduct);
				}
			}
		}

		return orderProducts;
	}
}