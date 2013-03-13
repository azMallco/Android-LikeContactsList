package com.vancl.likecontactlist.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vancl.likecontactlist.R;
import com.vancl.likecontactlist.model.BaseListModel;
import com.vancl.likecontactlist.utils.CharactorUtils;
import com.vancl.likecontactlist.widgets.ListSideBar;

public class ContactLikeListAdapter extends BaseAdapter{
	private Context context;
	private List<BaseListModel> models; 
	private LayoutInflater inflater;
	//���������״γ������б��е�λ��.
	private HashMap<String, Integer> alphaIndexer;
	//����ֵ,��ListSideBar�е�һ��.
	private String[] sections;
	
	private ListSideBar listSideBar;
	
	public ContactLikeListAdapter(Context context,List<BaseListModel> models,ListSideBar sideBar){
		this.context = context;
		this.models = models;
		this.inflater = LayoutInflater.from(context);
		this.alphaIndexer = new HashMap<String, Integer>();
		
		for (int i =0; i <models.size(); i++) {
			String name = CharactorUtils.getAlpha(models.get(i).getItemName());
			if(!alphaIndexer.containsKey(name)){
				//ֻ��¼��list���״γ��ֵ�λ��
				alphaIndexer.put(name, i);
			}
		}
		
		Set<String> sectionLetters = alphaIndexer.keySet();
		ArrayList<String> sectionList = new ArrayList<String>(
				sectionLetters);
		Collections.sort(sectionList);
		sections = new String[sectionList.size()];
		sectionList.toArray(sections);
		listSideBar = sideBar;
		
	}
	
	@Override
	public int getCount() {
		return models.size();
	}

	@Override
	public Object getItem(int arg0) {
		return models.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_item, null);
			holder = new ViewHolder();
			holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.number = (TextView) convertView
					.findViewById(R.id.number);
			holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		BaseListModel baseListModel = models.get(position);
		holder.name.setText(baseListModel.getItemName());
		holder.number.setVisibility(View.INVISIBLE);
		holder.imageView.setVisibility(View.INVISIBLE);
		
		// ��ǰ��ϵ�˵�sortKey
		String currentStr = CharactorUtils.getAlpha(baseListModel.getItemName());
		// ��һ����ϵ�˵�sortKey
		String previewStr = (position - 1) >= 0 ? CharactorUtils.getAlpha(models.get(
				position - 1).getItemName()) : " ";
		/**
		 * �ж���ʾ#��A-Z��TextView������ɼ�
		 */
		if (!previewStr.equals(currentStr)) { // ��ǰ��ϵ�˵�sortKey��=��һ����ϵ�˵�sortKey��˵����ǰ��ϵ�������顣
			holder.alpha.setVisibility(View.VISIBLE);
			holder.alpha.setText(currentStr);
		} else {
			holder.alpha.setVisibility(View.GONE);
		}
		return convertView;
	}

	public int scrollToString(String s){
		int index = -1;
		if(alphaIndexer.containsKey(s)){
			index = alphaIndexer.get(s);
		}
		return index;
	}
	
	private static class ViewHolder {
		TextView alpha;
		TextView name;
		TextView number;
		ImageView imageView;
	}
}
