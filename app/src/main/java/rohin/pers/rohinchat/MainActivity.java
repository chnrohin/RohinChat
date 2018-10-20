/*
 * Copyright © Rohin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package rohin.pers.rohinchat;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.lang.reflect.Method;
import java.util.ArrayList;

import butterknife.BindView;
import rohin.pers.rohinchat.base.BaseActivity;
import rohin.pers.rohinchat.base.BaseFragment;
import rohin.pers.rohinchat.common.Constans;
import rohin.pers.rohinchat.common.util.LogUtils;
import rohin.pers.rohinchat.common.util.PermissionUtils;
import rohin.pers.rohinchat.common.util.ToastUtils;
import rohin.pers.rohinchat.chat.view.ChatFragment;
import rohin.pers.rohinchat.data.source.local.IInitLocalDb;
import rohin.pers.rohinchat.data.source.local.InitLocalDb;
import rohin.pers.rohinchat.social.view.ContactsFragment;
import rohin.pers.rohinchat.user.view.MeFragment;
import rohin.pers.rohinchat.service.view.ServiceFragment;
import rohin.pers.rohinchat.social.view.ContactsDetailActivity;
import rohin.pers.rohinchat.social.view.AddContactsActivity;
import rohin.pers.rohinchat.data.eneity.Contacts;
import rohin.pers.rohinchat.data.eneity.SimpleChat;
import rohin.pers.rohinchat.chat.view.ChatWithActivity;
import rohin.pers.rohinchat.search.view.SearchActivity;
import rohin.pers.rohinchat.common.design.viewpage.transformer.ZoomOutPageTransformer;

/**
 * @author Rohin
 * @date 2018/7/15
 */
public class MainActivity extends BaseActivity implements ChatFragment.OnSimpleChatItemClickListener
        , ContactsFragment.OnContactItemClickListener {

    public static final int CHAT_WITH_REQUEST_CODE = 3306;
    public static final int CONTACTS_DETAIL_REQUEST_CODE = 3307;

    private static final int requestCode = 1111;

    @BindView(R2.id.botNavView_main_navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R2.id.activity_main_viewpager)
    ViewPager mViewPager;
    @BindView(R2.id.toolbar)
    Toolbar mToolbar;

    MenuItem mMenuItem;

    private ArrayList<Fragment> mFragmentList;

    private FragmentPagerAdapter fragmentPagerAdapter
            = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (null == mMenuItem) {
                mBottomNavigationView.getMenu().getItem(0).setChecked(false);
            } else {
                mMenuItem.setChecked(false);
            }
            mMenuItem = mBottomNavigationView.getMenu().getItem(position);
            mMenuItem.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
            switch (item.getItemId()) {
                case R.id.navigation_chat:
                    mViewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_contacts:
                    mViewPager.setCurrentItem(1);
                    break;
                case R.id.navigation_service:
                    mViewPager.setCurrentItem(2);
                    break;
                case R.id.navigation_me:
                    mViewPager.setCurrentItem(3);
                    break;
                default:
                    return false;
            }
            return true;
    };

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, MainActivity.class);
    }

    private void init() {
        initPermission();
        initDb();
        initFragment();
        initToolbar();
        initViewPager();
        initNavigation();
    }

    private void initPermission() {
        PermissionUtils.getInstance(requestCode).getDefaultPermission(this);
    }

    private void initDb() {
        IInitLocalDb initLocalDb = new InitLocalDb();
        initLocalDb.init();
    }

    private void initFragment() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ChatFragment.getInstance());
        mFragmentList.add(ContactsFragment.getInstance());
        mFragmentList.add(ServiceFragment.getInstance());
        mFragmentList.add(MeFragment.getInstance());
    }

    private void initToolbar() {

    }

    private void initViewPager() {
        mViewPager.setPageTransformer(false, new ZoomOutPageTransformer());
        // 该方法默认加载下一页 (ViewPager -> DEFAULT_OFFSCREEN_PAGES)
        mViewPager.setOffscreenPageLimit(0);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initNavigation() {
        mBottomNavigationView.setLabelVisibilityMode(1);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        String checkedColor;
        if (Build.VERSION.SDK_INT >= 23){
            checkedColor = String.valueOf(getColor(R.color.colorLogo));
        } else {
            // logoColor
            checkedColor = Constans.LOGO_COLOR;
        }
        int[][] states = new int[][]{
                new int[] {-android.R.attr.state_checked},
                new int[] {android.R.attr.state_checked}
        };
        int[] colors = new int[]{
                Build.VERSION.SDK_INT >= 23
                        ? getColor(R.color.bottomNavigationView_Uncheck_Color)
                        : Color.GRAY,
                Build.VERSION.SDK_INT >= 23
                        ? getColor(R.color.bottomNavigationView_Checked_Color)
                        : Color.parseColor(checkedColor)
        };
        ColorStateList colorStateList = new ColorStateList(states, colors);
        mBottomNavigationView.setItemIconTintList(colorStateList);
        mBottomNavigationView.setItemTextColor(colorStateList);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*
        当前 Activity 的 onPause 方法执行结束后才会创建（onCreate）或恢复
        （onRestart）别的 Activity，所以在 onPause 方法中不适合做耗时较长的工作，这
        会影响到页面之间的跳转效率。
         */
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void handleToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle(R.string.app_name);
    }

    @Override
    protected BaseFragment getFragmentInstance() {
        return null;
    }

    @Override
    protected int getContainerViewId() {
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (null != menu) {
            final String methodName = "MenuBuilder";
            if (methodName.equalsIgnoreCase(menu.getClass().getSimpleName())) {
                try {
                    // 使用反射来访问隐藏/私有的Android api是不安全的;
                    // 它通常不会在其他厂商的设备上工作，
                    // 它可能会突然停止工作（如果API被删除）
                    // 或者崩溃（如果API行为发生了变化，因为没有对兼容性的保证）。
                    final Method method = menu.getClass()
                            .getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    LogUtils.e("反射MenuBuilder显示菜单图标时发生异常！");
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                startActivity(SearchActivity.newIntent(this));
                overridePendingTransition(0, 0);
                return true;
            case R.id.action_new_chat:
                ToastUtils.showToast(this, "action new chat");
                return true;
            case R.id.action_add_contacts:
                startActivity(AddContactsActivity.newIntent(this));
                return true;
            case R.id.action_scan_qr_code:
                ToastUtils.showToast(this, "action scan qr code");
                return true;
            case R.id.action_help_feedback:
                ToastUtils.showToast(this, "action help&feedback");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 返回touch交互对象并序列化传递。
     *
     * @see ChatFragment.OnSimpleChatItemClickListener#onSimpleChatItemClick(SimpleChat)
     */
    @Override
    public void onSimpleChatItemClick(SimpleChat simpleChat) {
        /*
        总是使用显式 Intent 启动或者绑定 Service，且不要为服务声明 Intent Filter，
        保证应用的安全性。如果确实需要使用隐式调用，则可为 Service 提供 Intent Filter
        并从 Intent 中排除相应的组件名称，但必须搭配使用 Intent#setPackage()方法设置
        Intent 的指定包名，这样可以充分消除目标服务的不确定性。
         */
//        Intent intent = new Intent(this, ChatWithActivity.class);
        /*
        对于只用于应用内的广播，优先使用 LocalBroadcastManager 来进行注册
        和发送，LocalBroadcastManager 安全性更好，同时拥有更高的运行效率。
         */
        startActivityForResult(ChatWithActivity.newIntent(this, simpleChat),
                CHAT_WITH_REQUEST_CODE);
    }

    /**
     * 返回touch交互对象并序列化传递。
     *
     * @see ContactsFragment.OnContactItemClickListener#onContactItemClick(Contacts)
     */
    @Override
    public void onContactItemClick(Contacts contacts) {
        /*
        Android 基础组件如果使用隐式调用，应在 AndroidManifest.xml 中使用
        <intent-filter> 或在代码中使用 IntentFilter 增加过滤。
         */
        startActivityForResult(ContactsDetailActivity.newIntent(this, contacts),
                CONTACTS_DETAIL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHAT_WITH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Snackbar.make(mToolbar, data.getIntExtra(ChatWithActivity.EXCEPTION_NPE_CONTACTS
                        , R.string.error_null_contact), Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.close, (v -> {/* Do OnClickListener */})).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
