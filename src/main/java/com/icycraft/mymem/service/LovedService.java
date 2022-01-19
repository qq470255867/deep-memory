package com.icycraft.mymem.service;

import com.icycraft.mymem.entity.Loved;

public interface LovedService {


    Loved addLoved(Loved loved);

    Loved delLoved(Loved loved);

    Loved getLoved(Loved loved);


    int getMemLovedNum(long memId);

    int getUserLovedNum(long userId);
}
