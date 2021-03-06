package com.ghx.app.lulu.fragment.other;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ghx.app.R;
import com.ghx.app.base.BaseFragment;
import com.ghx.app.lulu.adapter.HomeItemRecylerViewAdapter;
import com.ghx.app.lulu.model.HomeItemRvItemModel;
import com.ghx.app.lulu.model.LunbotuBean;
import com.ghx.app.lulu.presenter.HomeItemFragmentPresenter;
import com.ghx.app.lulu.utils.LogUtil;
import com.ghx.app.lulu.utils.ToastUtil;
import com.ghx.app.lulu.view.IHomeItemFragmentView;
import com.ghx.app.lulu.weiget.autoscroll_viewpager.AutoScrollViewPager;
import com.ghx.app.lulu.weiget.pullloadmore_recyleview.PullLoadMoreRecyclerView;
import com.ghx.app.lulu.weiget.pullloadmore_recyleview.RecyclerViewHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guo_hx on 2016/9/26.17:10
 */

public class HomeItemFragment extends BaseFragment<HomeItemFragmentPresenter> implements IHomeItemFragmentView, PullLoadMoreRecyclerView.PullLoadMoreListener {

    private String TAG = getClass().getSimpleName();

    List<String> mPicUrlList = new ArrayList<>();

    private String mFlag;
    private int index = 0;

    private PullLoadMoreRecyclerView mRvPullLoadMore;
    private HomeItemRecylerViewAdapter mHomeItemRecylerViewAdapter;
    private RecyclerViewHeader mRvHead;
    private AutoScrollViewPager mVpAuto;

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_home_item;
    }

    @Override
    protected Class<HomeItemFragmentPresenter> getPresenter() {

        return HomeItemFragmentPresenter.class;
    }

    @Override
    protected void initAllWidget(View rootView) {
        mVpAuto = (AutoScrollViewPager) rootView.findViewById(R.id.vp_auto);
        mRvPullLoadMore = (PullLoadMoreRecyclerView) rootView.findViewById(R.id.rv_pull_load_more);
        mRvHead = (RecyclerViewHeader) rootView.findViewById(R.id.rv_head);
        RecyclerView recyclerView = mRvPullLoadMore.getRecyclerView();
        mRvPullLoadMore.setGridLayout(2);
        //显示下拉刷新
        mRvPullLoadMore.setRefreshing(true);
//        //设置上拉刷新文字
//        mRvPullLoadMore.setFooterViewText("loading");
//        //设置上拉刷新文字颜色
//        mRvPullLoadMore.setFooterViewTextColor(R.color.white_main);
//        //设置加载更多背景色
//        mRvPullLoadMore.setFooterViewBackgroundColor(R.color.black_main);
//        mRvPullLoadMore.setLinearLayout();

        mRvPullLoadMore.setOnPullLoadMoreListener(this);
//        //setEmptyView，演示空数据，可以提示“数据加载中”-
//        mRvPullLoadMore.setEmptyView(LayoutInflater.from(getContext()).inflate(R.layout.empty_view, null));
////        mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity());
////        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
////        getData();

        mHomeItemRecylerViewAdapter = new HomeItemRecylerViewAdapter(getActivity());
        mRvPullLoadMore.setAdapter(mHomeItemRecylerViewAdapter);
        mRvHead.attachTo(recyclerView);

    }

    @Override
    protected void clickView(View v) {

    }

    public void setFlag(String flag) {

        mFlag = flag;
    }

    @Override
    public void showAds(LunbotuBean response) {

        List<LunbotuBean.LunbotuItemBean> data = response.data;

        mVpAuto.setPhotoData(data);
        mVpAuto.setBorderAnimation(false);

    }

    @Override
    public void showItem(HomeItemRvItemModel body) {

        mRvPullLoadMore.setPullLoadMoreCompleted();
        if (index == 0) {

            mHomeItemRecylerViewAdapter.setData(body.data);
        }else {
            mHomeItemRecylerViewAdapter.addAllData(body.data);
        }

        mRvPullLoadMore.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        index = 0;
        ToastUtil.showToast("onRefresh");
        mRvPullLoadMore.setRefreshing(true);
//        mPresenter.getItemServerData(index);
        mPresenter.getAdsServerData();
    }

    @Override
    public void onLoadMore() {

        ToastUtil.showToast("onLoadMore");
//        mPresenter.getItemServerData(index++);
    }

    public PullLoadMoreRecyclerView getmRvPullLoadMore() {
        return mRvPullLoadMore;
    }


}
